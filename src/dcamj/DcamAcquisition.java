package dcamj;

import static org.bridj.Pointer.pointerTo;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;

import dcamapi.DCAMCAP_TRANSFERINFO;
import dcamapi.DcamapiLibrary;
import dcamapi.DcamapiLibrary.DCAMERR;
import dcamapi.DcamapiLibrary.DCAMWAIT_EVENT;
import dcamj.utils.StopWatch;

public class DcamAcquisition implements Closeable
{

	private final int mDeviceIndex;
	private int mWidth = 2048;
	private int mHeight = 2048;
	private final int mNumberOfBuffersByDefault = 64;
	private double mExposureInSeconds = 0.001;

	public enum TriggerType
	{
		Internal, Software, ExternalEdge, ExternalFastEdge, ExternalLevel
	};

	private TriggerType mTriggerType = TriggerType.Internal;

	private final boolean mShowErrors = false;
	private final boolean mDebug = false;

	private DcamDevice mDcamDevice;
	private DcamBufferControl mBufferControl;

	private Thread mAcquisitionThread;
	private DcamAquisitionRunnable mDcamAquisitionRunnable;
	private CountDownLatch mAcquisitionStartedSignal;
	private CountDownLatch mAcquisitionFinishedSignal;

	public volatile long mFrameIndex = 0;

	private final ArrayList<DcamAcquisitionListener> mListenersList = new ArrayList<DcamAcquisitionListener>();
	private DcamProperties mProperties;

	public DcamAcquisition(final int pDeviceIndex)
	{
		super();
		mDeviceIndex = pDeviceIndex;
	}

	public double setExposureInSeconds(final double exposure)
	{
		mExposureInSeconds = exposure;
		if (mProperties != null)
			mExposureInSeconds = mProperties.setAndGetExposure(mExposureInSeconds);
		return mExposureInSeconds;
	}

	public double getExposureInSeconds()
	{
		return mExposureInSeconds;
	}

	public int setFrameWidth(final int pWidth)
	{
		return mWidth = DcamProperties.roundto4(pWidth);
	}

	public int setFrameHeight(final int pHeight)
	{
		return mHeight = DcamProperties.roundto4(pHeight);
	}

	public void setFrameWidthAndHeight(	final int pWidth,
																			final int pHeight)
	{
		mWidth = DcamProperties.roundto4(pWidth);
		mHeight = DcamProperties.roundto4(pHeight);
	}

	public int getWidth()
	{
		return mWidth;
	}

	public int getHeight()
	{
		return mHeight;
	}

	public int getFrameBytesPerPixel()
	{
		return 2;
	}

	public int getFrameSizeInBytes()
	{
		return mWidth * mHeight * 2;
	}

	public void setTriggerType(final TriggerType pTriggerType)
	{
		mTriggerType = pTriggerType;
		if (mProperties != null)
		{
			if (mTriggerType == TriggerType.ExternalEdge)
			{
				mProperties.setInputTriggerToExternalEdge();
			}
			if (mTriggerType == TriggerType.ExternalFastEdge)
			{
				mProperties.setInputTriggerToExternalFastEdge();
			}
			else if (mTriggerType == TriggerType.ExternalLevel)
			{
				mProperties.setInputTriggerToExternalLevel();
			}
			else if (mTriggerType == TriggerType.Software)
			{
				mProperties.setInputTriggerToSoftware();
			}
			else if (mTriggerType == TriggerType.Internal)
			{
				mProperties.setInputTriggerToInternal();
			}
		}
	}

	public boolean isExternalTriggering()
	{
		return mTriggerType == TriggerType.ExternalEdge || mTriggerType == TriggerType.ExternalFastEdge
						|| mTriggerType == TriggerType.ExternalLevel;
	}

	public void addListener(final DcamAcquisitionListener pDcamAcquisitionListener)
	{
		if (!mListenersList.contains(pDcamAcquisitionListener))
			mListenersList.add(pDcamAcquisitionListener);
	}

	public boolean open()
	{
		System.out.println("DcamJ: Initializing the DCAM library:");
		DcamLibrary.initialize();

		final int lNumberOfDevices = DcamLibrary.getNumberOfDevices();
		System.out.format("DcamJ: Number of devices connected: %d \n",
											lNumberOfDevices);

		if (mDeviceIndex >= lNumberOfDevices)
		{
			System.out.format("DcamJ: device not found for index %d \n",
												mDeviceIndex);
			return false;
		}

		mDcamDevice = DcamLibrary.getDeviceForId(mDeviceIndex);
		mDcamDevice.mShowErrors = mShowErrors;
		mDcamDevice.mDebug = mDebug;

		mDcamDevice.displayDeviceInfo();

		// lProperties.listAllProperties();

		setCurrentProperties();

		return true;
	}

	public void reopen()
	{
		if (mBufferControl != null)
			mBufferControl.releaseBuffers();
		if (mDcamDevice != null)
			mDcamDevice.close();
		mDcamDevice = DcamLibrary.getDeviceForId(mDeviceIndex);
		setCurrentProperties();

	}

	private boolean provideExternalBuffers(final DcamFrame pDcamFrame)
	{
		/*System.out.format("DcamJ: provide %d external buffers for a total buffer capacity of %d\n",
											pDcamFrame.getDepth(),
											pDcamFrame.getBufferLengthInBytes());/**/
		mBufferControl = getBufferControl();
		mBufferControl.mShowErrors = true;
		mBufferControl.mDebug = false;
		return mBufferControl.attachExternalBuffers(pDcamFrame);
	}

	private boolean allocateInternalBuffers()
	{
		System.out.format("DcamJ: allocate %d internal buffers \n",
											mNumberOfBuffersByDefault);
		mBufferControl = getBufferControl();
		mBufferControl.mShowErrors = true;
		mBufferControl.mDebug = false;
		return mBufferControl.allocateInternalBuffers(mNumberOfBuffersByDefault);
	}

	public DcamProperties getProperties()
	{
		return mDcamDevice.getProperties();
	}

	private void setCurrentProperties()
	{
		mProperties = mDcamDevice.getProperties();
		mProperties.mShowErrors = mShowErrors;
		mProperties.mDebug = mDebug;

		final double lExposureRequested = mExposureInSeconds;
		final double lEffectiveExposure = setExposureInSeconds(lExposureRequested);
		System.out.format("DcamJ: exposure requested: %g, exposure set at: %g \n",
											lExposureRequested,
											lEffectiveExposure);

		if (mProperties != null)
			mProperties.setCenteredROI(mWidth, mHeight);
		System.out.format("DcamJ: Centered ROI set cwidth=%d cheight=%d \n",
											mWidth,
											mHeight);

		setTriggerType(mTriggerType);
	}

	public final DcamBufferControl getBufferControl()
	{
		if (mBufferControl == null)
		{
			mBufferControl = new DcamBufferControl(mDcamDevice, this);
		}
		return mBufferControl;
	}

	public final boolean startAcquisition()
	{
		return startAcquisition(true,
														false,
														allocateDefaultDcamFrame(mNumberOfBuffersByDefault));
	}

	private DcamFrame allocateDefaultDcamFrame(final int pNumberOfFramesToCapture)
	{
		return DcamFrame.requestFrame(getFrameBytesPerPixel(),
																	getWidth(),
																	getHeight(),
																	pNumberOfFramesToCapture);
	}

	public final boolean startAcquisition(final int pNumberOfFramesToCapture)
	{
		return startAcquisition(false,
														false,
														allocateDefaultDcamFrame(pNumberOfFramesToCapture));
	}

	public final boolean startAcquisition(final boolean pContinuousAcquisition,
																				final boolean pStackAcquisition,
																				final DcamFrame pDcamFrame)
	{
		return startAcquisition(pContinuousAcquisition,
														pStackAcquisition,
														true,
														!pContinuousAcquisition,
														pDcamFrame);
	}

	public final boolean startAcquisition(final boolean pContinuousAcquisition,
																				final boolean pStackAcquisition,
																				final boolean pWaitToStart,
																				final boolean pWaitToFinish,
																				final DcamFrame pDcamFrame)
	{

		if (!checkDimensions(pDcamFrame))
			return false;

		boolean mBufferAllocationSuccess = false;
		if (pDcamFrame != null)
			mBufferAllocationSuccess = provideExternalBuffers(pDcamFrame);

		if (!mBufferAllocationSuccess)
		{
			getBufferControl().displayErrorList();
			return false;
		}

		mAcquisitionStartedSignal = new CountDownLatch(1);
		mAcquisitionFinishedSignal = new CountDownLatch(1);
		mDcamAquisitionRunnable = new DcamAquisitionRunnable(	pDcamFrame.getDepth(),
																													pContinuousAcquisition,
																													pStackAcquisition);
		mAcquisitionThread = new Thread(mDcamAquisitionRunnable);
		mAcquisitionThread.setName("DcamAcquisitionThread");
		mAcquisitionThread.setDaemon(true);
		mAcquisitionThread.setPriority(Thread.MAX_PRIORITY);
		mAcquisitionThread.start();

		if (pWaitToStart)
			waitAcquisitionStarted();

		if (pWaitToFinish)
			waitAcquisitionFinishedAndStop();

		return !mDcamAquisitionRunnable.mTrueIfError;
	}

	private boolean checkDimensions(final DcamFrame pDcamFrame)
	{
		final boolean isEverythingFine = pDcamFrame.getWidth() == getWidth() && pDcamFrame.getHeight() == getHeight();
		return isEverythingFine;
	}
	
	private class DcamAquisitionRunnable implements Runnable
	{

		private volatile boolean mTrueIfStarted = false;
		private volatile boolean mStopContinousIfFalse = true;
		private volatile boolean mStopIfFalse = true;
		private volatile boolean mTrueIfStopped = false;
		private volatile boolean mTrueIfError = false;

		private final int mNumberOfFramesToCapture;
		private final boolean mContinuousAcquisition;
		private final boolean mStackAcquisition;

		public DcamAquisitionRunnable(final int pNumberOfFramesToCapture,
																	final boolean pContinuousAcquisition,
																	final boolean pStackAcquisition)
		{
			mNumberOfFramesToCapture = pNumberOfFramesToCapture;
			mContinuousAcquisition = pContinuousAcquisition;
			mStackAcquisition = pStackAcquisition;
		}

		@SuppressWarnings("unused")
		@Override
		public void run()
		{

			try
			{
				System.out.println("DcamJ: Starting acquisition:");

				mTrueIfStarted = true;
				mFrameIndex = 0;

				if (mStackAcquisition && mContinuousAcquisition)
					while (mStopContinousIfFalse)
					{
						runOnce();
					}
				else
				{
					runOnce();
				}

			}
			catch (final Throwable e)
			{
				e.printStackTrace();
				mTrueIfError = true;
			}
			finally
			{
				if (!mStackAcquisition && mContinuousAcquisition)
					mDcamDevice.stop();
				mTrueIfStopped = true;
				System.out.println("DcamJ: stopping acquisition:");
				mAcquisitionFinishedSignal.countDown();
			}

		}

		private void runOnce()
		{
			mStopIfFalse = true;

			if (mContinuousAcquisition && !mStackAcquisition)
			{
				if (mDebug)
					System.out.format("DcamJ: Starting continuous acquisition \n");
				mDcamDevice.startContinuous();
			}
			else
			{
				if (mDebug)
					System.out.format("DcamJ: Starting acquisition sequence of %d frames \n",
														mNumberOfFramesToCapture);/**/
				mDcamDevice.startSequence();
			}

			int lWaitTimeout;

			if (mStackAcquisition)
				lWaitTimeout = 3000; // + (int) (10 * 1000 * mNumberOfFramesToCapture *
															// mExposureInSeconds)
			else
			{
				if (isExternalTriggering())
					lWaitTimeout = 5000;
				else
					lWaitTimeout = 3000;
			}

			if (mDebug)
				System.out.format("DcamJ: DcamWait timeout set to %d ms \n",
													lWaitTimeout);/**/

			final int lNumberOfBuffers = getBufferControl().getNumberOfSinglePlaneBuffers();
			int lLocalFrameIndex = 0;

			while (mStopIfFalse)
			{

				// DCAMCAP_EVENT_FRAMEREADYORSTOPPED(2|16),
				final DCAMWAIT_EVENT lDcamcapEventToWaitFor;

				if (mContinuousAcquisition && !mStackAcquisition)
					lDcamcapEventToWaitFor = DCAMWAIT_EVENT.DCAMCAP_EVENT_FRAMEREADYORSTOPPED;
				else if (mStackAcquisition)
					lDcamcapEventToWaitFor = DCAMWAIT_EVENT.DCAMCAP_EVENT_FRAMEREADYORSTOPPED;
				else
					lDcamcapEventToWaitFor = DCAMWAIT_EVENT.DCAMCAP_EVENT_FRAMEREADY;

				mAcquisitionStartedSignal.countDown();
				if (mDebug)
					System.out.print("waitForEvent.before... ");
				final boolean lWaitSuccess = (mDcamDevice.getDcamWait().waitForEvent(	lDcamcapEventToWaitFor,
																																							lWaitTimeout));
				if (mDebug)
					System.out.println(" ...after.");
				final long lArrivalTimeStampInNanoseconds = StopWatch.absoluteTimeInNanoseconds();
				//System.out.println(System.nanoTime());

				if (!lWaitSuccess)
				{
					System.err.println("DcamJ: waiting for event failed!!!!");
					System.err.format("DcamJ: frame index = %d (local index = %d) out of %d frames to capture (%s acquisition)  \n",
														mFrameIndex,
														lLocalFrameIndex,
														mNumberOfFramesToCapture,
														mStackAcquisition	? "stack"
																							: "single plane");
					if (!isExternalTriggering())
					{
						System.err.println("DcamJ: timeout waiting for frame!");
						break;
					}
					continue;
				}

				final long lDcamWaitEvent = mDcamDevice.getDcamWait()
																								.getEvent();
				final boolean lReceivedStopEvent = lDcamWaitEvent == DCAMWAIT_EVENT.DCAMCAP_EVENT_STOPPED.value;
				final boolean lReceivedFrameReadyEvent = lDcamWaitEvent == DCAMWAIT_EVENT.DCAMCAP_EVENT_FRAMEREADY.value;

				DcamFrame lDcamFrame = null;

				if (mStackAcquisition && lReceivedStopEvent)
				{
					if (mDebug)
						System.out.println("DcamJ: Received Stop Event");
					if (mStackAcquisition)
					{
						lDcamFrame = getBufferControl().getStackDcamFrame();
						lDcamFrame.setIndex(mFrameIndex);
						lDcamFrame.setTimeStampInNs(lArrivalTimeStampInNanoseconds);
						notifyListeners(mFrameIndex,
														lArrivalTimeStampInNanoseconds,
														0,
														lDcamFrame);
						mFrameIndex++;
						mStopIfFalse = false;
					}
				}

				if (!mStackAcquisition && lReceivedFrameReadyEvent)
				{
					lDcamFrame = getBufferControl().getDcamFrameForIndex(lLocalFrameIndex);
					lDcamFrame.setIndex(mFrameIndex);
					lDcamFrame.setTimeStampInNs(lArrivalTimeStampInNanoseconds);
					notifyListeners(mFrameIndex,
													lArrivalTimeStampInNanoseconds,
													lLocalFrameIndex,
													lDcamFrame);
					mFrameIndex++;
				}

				if (lDcamFrame != null)
				{
					if (mDebug)
						System.out.format("DcamJ: frame index = %d (local index = %d) \n",
															mFrameIndex,
															lLocalFrameIndex);

					lDcamFrame.setIndex(mFrameIndex);
					lDcamFrame.setTimeStampInNs(lArrivalTimeStampInNanoseconds);
				}

				if (lReceivedFrameReadyEvent)
				{
					if (mDebug)
						System.out.println("DcamJ: Received frame ready Event");

					if (!mContinuousAcquisition && !mStackAcquisition
							&& lLocalFrameIndex >= mNumberOfFramesToCapture - 1)
					{
						mStopIfFalse = false;
					}

					lLocalFrameIndex = (lLocalFrameIndex + 1) % lNumberOfBuffers;
				}

			}

			// if (!mContinuousAcquisition)
			{
				// System.out.println("getTransferinfo.before");
				final DCAMCAP_TRANSFERINFO lTransferinfo = getTransferinfo();
				// System.out.println("getTransferinfo.after");

				final int lNumberOfFramesWrittentoExternalBuffer = (int) lTransferinfo.nFrameCount();

				if (mDebug)
					System.out.format("DcamJ: Wrote %d frames into external buffers (local frame index=%d) \n",
														lNumberOfFramesWrittentoExternalBuffer,
														lLocalFrameIndex);/**/

				final boolean lWrongNumberofFramesAcquired = lNumberOfFramesWrittentoExternalBuffer != mNumberOfFramesToCapture;
				if (lWrongNumberofFramesAcquired)
				{
					System.err.format("DcamJ: Wrong number of frames acquired!\n");
					mTrueIfError = true;
				}

			}
		}

	};

	private void notifyListeners(	final long pAbsoluteFrameIndex,
																final long pArrivalTimeStampInNanoseconds,
																final int pFrameIndexInBufferList,
																final DcamFrame pDcamFrame)
	{
		for (final DcamAcquisitionListener lDcamAcquisitionListener : mListenersList)
		{
			lDcamAcquisitionListener.frameArrived(this,
																						pAbsoluteFrameIndex,
																						pArrivalTimeStampInNanoseconds,
																						pFrameIndexInBufferList,
																						pDcamFrame);
		}

	}

	public final void stopAcquisition()
	{
		if (mDcamAquisitionRunnable == null)
			return;
		mDcamAquisitionRunnable.mStopIfFalse = false;
		mDcamAquisitionRunnable.mStopContinousIfFalse = false;
		waitAcquisitionFinishedAndStop();
	}

	private void waitAcquisitionStarted()
	{
		try
		{
			mAcquisitionStartedSignal.await();
		}
		catch (final InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private void waitAcquisitionFinishedAndStop()
	{
		try
		{
			mAcquisitionFinishedSignal.await();
			mDcamDevice.stop();
		}
		catch (final InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public final boolean isAcquiring()
	{
		if (mDcamAquisitionRunnable == null)
			return false;
		return mDcamAquisitionRunnable.mTrueIfStarted && !mDcamAquisitionRunnable.mTrueIfStopped;
	}

	@Override
	public final void close()
	{
		System.out.println("mBufferControl.releaseBuffers();");
		try
		{
			Thread.sleep(2000);
		}
		catch (final InterruptedException e)
		{
			e.printStackTrace();
		}
		if (mBufferControl != null)
			mBufferControl.releaseBuffers();
		try
		{
			Thread.sleep(2000);
		}
		catch (final InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("mDcamDevice.close();");

		if (mDcamDevice != null)
			mDcamDevice.close();
		try
		{
			Thread.sleep(2000);
		}
		catch (final InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("DcamLibrary.uninitialize();");
		DcamLibrary.uninitialize();
		try
		{
			Thread.sleep(2000);
		}
		catch (final InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	DCAMCAP_TRANSFERINFO mDcamCapTransfertInfo;

	private DCAMCAP_TRANSFERINFO getTransferinfo()
	{
		mDcamCapTransfertInfo = new DCAMCAP_TRANSFERINFO();
		mDcamCapTransfertInfo.size(BridJ.sizeOf(DCAMCAP_TRANSFERINFO.class));
		final IntValuedEnum<DCAMERR> lError = DcamapiLibrary.dcamcapTransferinfo(	mDcamDevice.getHDCAMPointer(),
																																							pointerTo(mDcamCapTransfertInfo));

		return mDcamCapTransfertInfo;
	}



}
