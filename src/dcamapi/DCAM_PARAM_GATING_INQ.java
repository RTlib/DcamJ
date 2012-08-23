package dcamapi;

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * <i>native declaration : lib/dcam/inc/dcamapi3.h</i><br>
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
public class DCAM_PARAM_GATING_INQ extends StructObject
{
	public DCAM_PARAM_GATING_INQ()
	{
		super();
	}

	/**
	 * id = DCAM_IDPARAM_GATING_INQ<br>
	 * C type : DCAM_HDR_PARAM
	 */
	@Field(0)
	public DCAM_HDR_PARAM hdr()
	{
		return this.io.getNativeObjectField(this, 0);
	}

	/**
	 * id = DCAM_IDPARAM_GATING_INQ<br>
	 * C type : DCAM_HDR_PARAM
	 */
	@Field(0)
	public DCAM_PARAM_GATING_INQ hdr(final DCAM_HDR_PARAM hdr)
	{
		this.io.setNativeObjectField(this, 0, hdr);
		return this;
	}

	/**
	 * [in/out] trigger mode that supports this parameter.<br>
	 * C type : _DWORD
	 */
	@CLong
	@Field(1)
	public long trigmode()
	{
		return this.io.getCLongField(this, 1);
	}

	/**
	 * [in/out] trigger mode that supports this parameter.<br>
	 * C type : _DWORD
	 */
	@CLong
	@Field(1)
	public DCAM_PARAM_GATING_INQ trigmode(final long trigmode)
	{
		this.io.setCLongField(this, 1, trigmode);
		return this;
	}

	// / [out] gating capability
	@Field(2)
	public int capflags()
	{
		return this.io.getIntField(this, 2);
	}

	// / [out] gating capability
	@Field(2)
	public DCAM_PARAM_GATING_INQ capflags(final int capflags)
	{
		this.io.setIntField(this, 2, capflags);
		return this;
	}

	// / [out] minimum time of interval by second.
	@Field(3)
	public double intervalmin()
	{
		return this.io.getDoubleField(this, 3);
	}

	// / [out] minimum time of interval by second.
	@Field(3)
	public DCAM_PARAM_GATING_INQ intervalmin(final double intervalmin)
	{
		this.io.setDoubleField(this, 3, intervalmin);
		return this;
	}

	// / [out] maximum time of interval by second.
	@Field(4)
	public double intervalmax()
	{
		return this.io.getDoubleField(this, 4);
	}

	// / [out] maximum time of interval by second.
	@Field(4)
	public DCAM_PARAM_GATING_INQ intervalmax(final double intervalmax)
	{
		this.io.setDoubleField(this, 4, intervalmax);
		return this;
	}

	// / [out] step time of interval by second. This was intervalystep
	@Field(5)
	public double intervalstep()
	{
		return this.io.getDoubleField(this, 5);
	}

	// / [out] step time of interval by second. This was intervalystep
	@Field(5)
	public DCAM_PARAM_GATING_INQ intervalstep(final double intervalstep)
	{
		this.io.setDoubleField(this, 5, intervalstep);
		return this;
	}

	// / [out] minimum time of delay by second.
	@Field(6)
	public double delaymin()
	{
		return this.io.getDoubleField(this, 6);
	}

	// / [out] minimum time of delay by second.
	@Field(6)
	public DCAM_PARAM_GATING_INQ delaymin(final double delaymin)
	{
		this.io.setDoubleField(this, 6, delaymin);
		return this;
	}

	// / [out] maximum time of delay by second.
	@Field(7)
	public double delaymax()
	{
		return this.io.getDoubleField(this, 7);
	}

	// / [out] maximum time of delay by second.
	@Field(7)
	public DCAM_PARAM_GATING_INQ delaymax(final double delaymax)
	{
		this.io.setDoubleField(this, 7, delaymax);
		return this;
	}

	// / [out] step time of delay by second.
	@Field(8)
	public double delaystep()
	{
		return this.io.getDoubleField(this, 8);
	}

	// / [out] step time of delay by second.
	@Field(8)
	public DCAM_PARAM_GATING_INQ delaystep(final double delaystep)
	{
		this.io.setDoubleField(this, 8, delaystep);
		return this;
	}

	// / [out] minimum time of width by second.
	@Field(9)
	public double widthmin()
	{
		return this.io.getDoubleField(this, 9);
	}

	// / [out] minimum time of width by second.
	@Field(9)
	public DCAM_PARAM_GATING_INQ widthmin(final double widthmin)
	{
		this.io.setDoubleField(this, 9, widthmin);
		return this;
	}

	// / [out] maximum time of width by second.
	@Field(10)
	public double widthmax()
	{
		return this.io.getDoubleField(this, 10);
	}

	// / [out] maximum time of width by second.
	@Field(10)
	public DCAM_PARAM_GATING_INQ widthmax(final double widthmax)
	{
		this.io.setDoubleField(this, 10, widthmax);
		return this;
	}

	// / [out] step time of width by second.
	@Field(11)
	public double widthstep()
	{
		return this.io.getDoubleField(this, 11);
	}

	// / [out] step time of width by second.
	@Field(11)
	public DCAM_PARAM_GATING_INQ widthstep(final double widthstep)
	{
		this.io.setDoubleField(this, 11, widthstep);
		return this;
	}

	public DCAM_PARAM_GATING_INQ(final Pointer pointer)
	{
		super(pointer);
	}
}
