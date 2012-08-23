package dcamapi;

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * <i>native declaration : lib\dcam\inc\dcamapi.h</i><br>
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
public class HDCAMREC_struct extends StructObject
{
	public HDCAMREC_struct()
	{
		super();
	}

	@CLong
	@Field(0)
	public long data1()
	{
		return this.io.getCLongField(this, 0);
	}

	@CLong
	@Field(0)
	public HDCAMREC_struct data1(final long data1)
	{
		this.io.setCLongField(this, 0, data1);
		return this;
	}

	@CLong
	@Field(1)
	public long data2()
	{
		return this.io.getCLongField(this, 1);
	}

	@CLong
	@Field(1)
	public HDCAMREC_struct data2(final long data2)
	{
		this.io.setCLongField(this, 1, data2);
		return this;
	}

	public HDCAMREC_struct(final Pointer pointer)
	{
		super(pointer);
	}
}
