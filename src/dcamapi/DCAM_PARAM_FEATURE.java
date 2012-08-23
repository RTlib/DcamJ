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
public class DCAM_PARAM_FEATURE extends StructObject
{
	public DCAM_PARAM_FEATURE()
	{
		super();
	}

	/**
	 * id == DCAM_IDPARAM_FEATURE<br>
	 * C type : DCAM_HDR_PARAM
	 */
	@Field(0)
	public DCAM_HDR_PARAM hdr()
	{
		return this.io.getNativeObjectField(this, 0);
	}

	/**
	 * id == DCAM_IDPARAM_FEATURE<br>
	 * C type : DCAM_HDR_PARAM
	 */
	@Field(0)
	public DCAM_PARAM_FEATURE hdr(final DCAM_HDR_PARAM hdr)
	{
		this.io.setNativeObjectField(this, 0, hdr);
		return this;
	}

	/**
	 * [in]<br>
	 * C type : _DWORD
	 */
	@CLong
	@Field(1)
	public long featureid()
	{
		return this.io.getCLongField(this, 1);
	}

	/**
	 * [in]<br>
	 * C type : _DWORD
	 */
	@CLong
	@Field(1)
	public DCAM_PARAM_FEATURE featureid(final long featureid)
	{
		this.io.setCLongField(this, 1, featureid);
		return this;
	}

	/**
	 * [in/out]<br>
	 * C type : _DWORD
	 */
	@CLong
	@Field(2)
	public long flags()
	{
		return this.io.getCLongField(this, 2);
	}

	/**
	 * [in/out]<br>
	 * C type : _DWORD
	 */
	@CLong
	@Field(2)
	public DCAM_PARAM_FEATURE flags(final long flags)
	{
		this.io.setCLongField(this, 2, flags);
		return this;
	}

	// / [in/out]
	@Field(3)
	public float featurevalue()
	{
		return this.io.getFloatField(this, 3);
	}

	// / [in/out]
	@Field(3)
	public DCAM_PARAM_FEATURE featurevalue(final float featurevalue)
	{
		this.io.setFloatField(this, 3, featurevalue);
		return this;
	}

	public DCAM_PARAM_FEATURE(final Pointer pointer)
	{
		super(pointer);
	}
}
