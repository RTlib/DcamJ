package dcamapi;

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * <i>native declaration : lib\dcam\inc\dcamapi.h:333</i><br>
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
public class DCAMAPI_INIT extends StructObject
{
	public DCAMAPI_INIT()
	{
		super();
	}

	// / [in]
	@CLong
	@Field(0)
	public long size()
	{
		return this.io.getCLongField(this, 0);
	}

	// / [in]
	@CLong
	@Field(0)
	public DCAMAPI_INIT size(final long size)
	{
		this.io.setCLongField(this, 0, size);
		return this;
	}

	// / [out]
	@CLong
	@Field(1)
	public long iDeviceCount()
	{
		return this.io.getCLongField(this, 1);
	}

	// / [out]
	@CLong
	@Field(1)
	public DCAMAPI_INIT iDeviceCount(final long iDeviceCount)
	{
		this.io.setCLongField(this, 1, iDeviceCount);
		return this;
	}

	// / [in]
	@CLong
	@Field(2)
	public long apiver()
	{
		return this.io.getCLongField(this, 2);
	}

	// / [in]
	@CLong
	@Field(2)
	public DCAMAPI_INIT apiver(final long apiver)
	{
		this.io.setCLongField(this, 2, apiver);
		return this;
	}

	// / 0
	@CLong
	@Field(3)
	public long reserved()
	{
		return this.io.getCLongField(this, 3);
	}

	// / 0
	@CLong
	@Field(3)
	public DCAMAPI_INIT reserved(final long reserved)
	{
		this.io.setCLongField(this, 3, reserved);
		return this;
	}

	/**
	 * [in ptr] 0 terminated long pointer.<br>
	 * C type : const long*
	 */
	@Field(4)
	public Pointer<CLong> initoption()
	{
		return this.io.getPointerField(this, 4);
	}

	/**
	 * [in ptr] 0 terminated long pointer.<br>
	 * C type : const long*
	 */
	@Field(4)
	public DCAMAPI_INIT initoption(final Pointer<CLong> initoption)
	{
		this.io.setPointerField(this, 4, initoption);
		return this;
	}

	/**
	 * [in ptr]<br>
	 * C type : const DCAM_GUID*
	 */
	@Field(5)
	public Pointer<DCAM_GUID> guid()
	{
		return this.io.getPointerField(this, 5);
	}

	/**
	 * [in ptr]<br>
	 * C type : const DCAM_GUID*
	 */
	@Field(5)
	public DCAMAPI_INIT guid(final Pointer<DCAM_GUID> guid)
	{
		this.io.setPointerField(this, 5, guid);
		return this;
	}

	public DCAMAPI_INIT(final Pointer pointer)
	{
		super(pointer);
	}
}
