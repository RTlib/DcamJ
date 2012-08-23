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
public class DCAM_PARAM_SCANMODE_INQ extends StructObject
{
	public DCAM_PARAM_SCANMODE_INQ()
	{
		super();
	}

	/**
	 * id == DCAM_IDPARAM_SCANMODE_INQ<br>
	 * C type : DCAM_HDR_PARAM
	 */
	@Field(0)
	public DCAM_HDR_PARAM hdr()
	{
		return this.io.getNativeObjectField(this, 0);
	}

	/**
	 * id == DCAM_IDPARAM_SCANMODE_INQ<br>
	 * C type : DCAM_HDR_PARAM
	 */
	@Field(0)
	public DCAM_PARAM_SCANMODE_INQ hdr(final DCAM_HDR_PARAM hdr)
	{
		this.io.setNativeObjectField(this, 0, hdr);
		return this;
	}

	// / [out]
	@Field(1)
	public int speedmax()
	{
		return this.io.getIntField(this, 1);
	}

	// / [out]
	@Field(1)
	public DCAM_PARAM_SCANMODE_INQ speedmax(final int speedmax)
	{
		this.io.setIntField(this, 1, speedmax);
		return this;
	}

	// / [in/out]
	@Field(2)
	public int special()
	{
		return this.io.getIntField(this, 2);
	}

	// / [in/out]
	@Field(2)
	public DCAM_PARAM_SCANMODE_INQ special(final int special)
	{
		this.io.setIntField(this, 2, special);
		return this;
	}

	public DCAM_PARAM_SCANMODE_INQ(final Pointer pointer)
	{
		super(pointer);
	}
}
