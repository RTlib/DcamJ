package dcamapi;

import org.bridj.Pointer;
import org.bridj.StructObject;
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
public class DCAM_PARAM_GATING extends StructObject
{
	public DCAM_PARAM_GATING()
	{
		super();
	}

	/**
	 * id == DCAM_IDPARAM_GATING<br>
	 * C type : DCAM_HDR_PARAM
	 */
	@Field(0)
	public DCAM_HDR_PARAM hdr()
	{
		return this.io.getNativeObjectField(this, 0);
	}

	/**
	 * id == DCAM_IDPARAM_GATING<br>
	 * C type : DCAM_HDR_PARAM
	 */
	@Field(0)
	public DCAM_PARAM_GATING hdr(final DCAM_HDR_PARAM hdr)
	{
		this.io.setNativeObjectField(this, 0, hdr);
		return this;
	}

	// / [in/out] gating control
	@Field(1)
	public int flags()
	{
		return this.io.getIntField(this, 1);
	}

	// / [in/out] gating control
	@Field(1)
	public DCAM_PARAM_GATING flags(final int flags)
	{
		this.io.setIntField(this, 1, flags);
		return this;
	}

	// / [in/out] interval time by second
	@Field(2)
	public double interval()
	{
		return this.io.getDoubleField(this, 2);
	}

	// / [in/out] interval time by second
	@Field(2)
	public DCAM_PARAM_GATING interval(final double interval)
	{
		this.io.setDoubleField(this, 2, interval);
		return this;
	}

	// / [in/out] delay time by second
	@Field(3)
	public double delay()
	{
		return this.io.getDoubleField(this, 3);
	}

	// / [in/out] delay time by second
	@Field(3)
	public DCAM_PARAM_GATING delay(final double delay)
	{
		this.io.setDoubleField(this, 3, delay);
		return this;
	}

	// / [in/out] gating period by second.
	@Field(4)
	public double width()
	{
		return this.io.getDoubleField(this, 4);
	}

	// / [in/out] gating period by second.
	@Field(4)
	public DCAM_PARAM_GATING width(final double width)
	{
		this.io.setDoubleField(this, 4, width);
		return this;
	}

	public DCAM_PARAM_GATING(final Pointer pointer)
	{
		super(pointer);
	}
}
