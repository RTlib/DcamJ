package dcamapi;

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * <i>native declaration : lib\dcam\inc\dcamapi.h:405</i><br>
 * This file was autogenerated by <a
 * href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a
 * href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few
 * opensource projects.</a>.<br>
 * For help, please visit <a
 * href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a
 * href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("dcamapi")
public class DCAMWAIT_OPEN extends StructObject
{
	public DCAMWAIT_OPEN()
	{
		super();
	}

	@CLong
	@Field(0)
	public long size()
	{
		return this.io.getCLongField(this, 0);
	}

	@CLong
	@Field(0)
	public DCAMWAIT_OPEN size(final long size)
	{
		this.io.setCLongField(this, 0, size);
		return this;
	}

	// / [out];
	@CLong
	@Field(1)
	public long supportevent()
	{
		return this.io.getCLongField(this, 1);
	}

	// / [out];
	@CLong
	@Field(1)
	public DCAMWAIT_OPEN supportevent(final long supportevent)
	{
		this.io.setCLongField(this, 1, supportevent);
		return this;
	}

	/**
	 * [out];<br>
	 * C type : HDCAMWAIT
	 */
	@Field(2)
	public Pointer<HDCAMWAIT_struct> hwait()
	{
		return this.io.getPointerField(this, 2);
	}

	/**
	 * [out];<br>
	 * C type : HDCAMWAIT
	 */
	@Field(2)
	public DCAMWAIT_OPEN hwait(final Pointer<HDCAMWAIT_struct> hwait)
	{
		this.io.setPointerField(this, 2, hwait);
		return this;
	}

	/**
	 * [in];<br>
	 * C type : HDCAM
	 */
	@Field(3)
	public Pointer<HDCAM_struct> hdcam()
	{
		return this.io.getPointerField(this, 3);
	}

	/**
	 * [in];<br>
	 * C type : HDCAM
	 */
	@Field(3)
	public DCAMWAIT_OPEN hdcam(final Pointer<HDCAM_struct> hdcam)
	{
		this.io.setPointerField(this, 3, hdcam);
		return this;
	}

	public DCAMWAIT_OPEN(final Pointer pointer)
	{
		super(pointer);
	}
}
