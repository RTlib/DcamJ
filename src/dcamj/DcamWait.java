package dcamj;

import static org.bridj.Pointer.pointerTo;

import java.io.Closeable;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;

import dcamapi.DCAMWAIT_OPEN;
import dcamapi.DCAMWAIT_START;
import dcamapi.DcamapiLibrary;
import dcamapi.DcamapiLibrary.DCAMERR;
import dcamapi.DcamapiLibrary.DCAMWAIT_EVENT;
import dcamapi.HDCAMWAIT_struct;

public class DcamWait extends DcamBase implements Closeable
{
	private final DcamDevice mDcamDevice;
	private Pointer<HDCAMWAIT_struct> mHwaitPointer = null;

	public DcamWait(final DcamDevice pDcamDevice)
	{
		super();
		mDcamDevice = pDcamDevice;
		open();
	}

	private void open()
	{
		final DCAMWAIT_OPEN lDCAMWAIT_OPEN = new DCAMWAIT_OPEN();
		lDCAMWAIT_OPEN.size(BridJ.sizeOf(DCAMWAIT_OPEN.class));
		lDCAMWAIT_OPEN.hdcam(mDcamDevice.getHDCAMPointer());
		final IntValuedEnum<DCAMERR> lError = DcamapiLibrary.dcamwaitOpen(pointerTo(lDCAMWAIT_OPEN));

		final boolean lSuccess = addErrorToListAndCheckHasSucceeded(lError);

		if (lSuccess)
		{
			mHwaitPointer = lDCAMWAIT_OPEN.hwait();
		}
	}

	public final boolean waitForEvent(final DCAMWAIT_EVENT pDCAMWAIT_EVENT,
																		final long pTimeOut)
	{
		if (mHwaitPointer == null)
		{
			return false;
		}

		final DCAMWAIT_START lDCAMWAIT_START = new DCAMWAIT_START();
		lDCAMWAIT_START.size(BridJ.sizeOf(DCAMWAIT_START.class));
		lDCAMWAIT_START.eventmask(pDCAMWAIT_EVENT.value);
		lDCAMWAIT_START.timeout(pTimeOut);

		final IntValuedEnum<DCAMERR> lError = DcamapiLibrary.dcamwaitStart(	mHwaitPointer,
																																				pointerTo(lDCAMWAIT_START));
		final boolean lSuccess = addErrorToListAndCheckHasSucceeded(lError);
		return lSuccess;
	}

	public final boolean abort()
	{
		if (mHwaitPointer == null)
		{
			return false;
		}

		final IntValuedEnum<DCAMERR> lError = DcamapiLibrary.dcamwaitAbort(mHwaitPointer);
		final boolean lSuccess = addErrorToListAndCheckHasSucceeded(lError);
		return lSuccess;
	}

	@Override
	public final void close()
	{
		if (mHwaitPointer == null)
		{
			return;
		}

		final IntValuedEnum<DCAMERR> lError = DcamapiLibrary.dcamwaitClose(mHwaitPointer);
		addErrorToListAndCheckHasSucceeded(lError);
		return;
	}

}
