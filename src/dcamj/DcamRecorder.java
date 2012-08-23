package dcamj;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import dcamj.utils.StopWatch;

public class DcamRecorder implements Closeable
{

	private FileChannel mFileChannel;
	protected boolean mActive = false;

	private final LinkedBlockingQueue<DcamFrame> mFrameQueue;
	private volatile long mElapsedTimeForWritingLastFrame;

	public DcamRecorder(final int pMaxQueueSize)
	{
		super();
		mFrameQueue = new LinkedBlockingQueue<DcamFrame>(pMaxQueueSize);
	}

	public final boolean open(File pFile)
	{
		try
		{
			mFileChannel = new FileOutputStream(pFile).getChannel();
			return true;
		}
		catch (FileNotFoundException e)
		{
			System.err.println(e.getLocalizedMessage());
			return false;
		}
	}

	public final boolean startDeamon()
	{
		Runnable lRunnable = new Runnable()
		{

			@Override
			public void run()
			{
				mActive = true;
				while (mActive)
				{
					try
					{
						DcamFrame lTake = mFrameQueue.take();
						writeToFile(lTake);
					}
					catch (InterruptedException e)
					{
					}
					catch (Throwable e)
					{
						System.out.println(e.getLocalizedMessage());
					}
				}
				try
				{
					mFileChannel.close();
				}
				catch (IOException e)
				{
					System.err.println(e.getLocalizedMessage());
				}

			}
		};
		Thread lThread = new Thread(lRunnable);
		lThread.setDaemon(true);
		lThread.setName("DcamRecorderDeamon");
		lThread.start();
		return true;

	}

	public boolean asynchronousWrite(DcamFrame pDcamFrame)
	{
		try
		{
			mFrameQueue.put(pDcamFrame);
			return true;
		}
		catch (InterruptedException e)
		{
			System.err.println(e.getLocalizedMessage());
			return false;
		}

	}

	public int getQueueLength()
	{
		return mFrameQueue.size();
	}
	
	public int getRemainingCapacity()
	{
		return mFrameQueue.remainingCapacity();
	}

	public boolean writeToFile(DcamFrame pFrameQueue)
	{
		try
		{
			final ByteBuffer lByteBuffer = pFrameQueue.getBytesDirectBuffer();
			StopWatch lStopWatch = StopWatch.start();
			final int limit= lByteBuffer.limit();
			lByteBuffer.limit(limit/2);
			mFileChannel.write(lByteBuffer);
			mFileChannel.force(false);
			lByteBuffer.limit(limit);
			mElapsedTimeForWritingLastFrame = lStopWatch.time(TimeUnit.MILLISECONDS);
			//System.out.format("Writing one frame to disk required: %d milliseconds \n",lElapsedTime);
			return true;
		}
		catch (IOException e)
		{
			System.err.println(e.getLocalizedMessage());
			return false;
		}
	}

	@Override
	public void close() throws IOException
	{
		mActive = false;
		mFrameQueue.clear();
	}

	public long getElapsedTimeForWritingLastFrame()
	{
		return mElapsedTimeForWritingLastFrame;
	}


}
