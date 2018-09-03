package dcamapi;

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * <i>native declaration : lib/dcam/inc/dcamapi3.h</i><br>
 * This file was autogenerated by
 * <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that
 * <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a
 * few opensource projects.</a>.<br>
 * For help, please visit
 * <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or
 * <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("dcamapi")
public class DCAM_HDR_PARAM extends StructObject
{
  public DCAM_HDR_PARAM()
  {
    super();
  }

  /**
   * size of whole structure<br>
   * C type : _DWORD
   */
  @CLong
  @Field(0)
  public long cbSize()
  {
    return this.io.getCLongField(this, 0);
  }

  /**
   * size of whole structure<br>
   * C type : _DWORD
   */
  @CLong
  @Field(0)
  public DCAM_HDR_PARAM cbSize(final long cbSize)
  {
    this.io.setCLongField(this, 0, cbSize);
    return this;
  }

  /**
   * specify the kind of this structure<br>
   * C type : _DWORD
   */
  @CLong
  @Field(1)
  public long id()
  {
    return this.io.getCLongField(this, 1);
  }

  /**
   * specify the kind of this structure<br>
   * C type : _DWORD
   */
  @CLong
  @Field(1)
  public DCAM_HDR_PARAM id(final long id)
  {
    this.io.setCLongField(this, 1, id);
    return this;
  }

  /**
   * specify the member to be set or requested by application<br>
   * C type : _DWORD
   */
  @CLong
  @Field(2)
  public long iFlag()
  {
    return this.io.getCLongField(this, 2);
  }

  /**
   * specify the member to be set or requested by application<br>
   * C type : _DWORD
   */
  @CLong
  @Field(2)
  public DCAM_HDR_PARAM iFlag(final long iFlag)
  {
    this.io.setCLongField(this, 2, iFlag);
    return this;
  }

  /**
   * specify the member to be set or gotten by module<br>
   * C type : _DWORD
   */
  @CLong
  @Field(3)
  public long oFlag()
  {
    return this.io.getCLongField(this, 3);
  }

  /**
   * specify the member to be set or gotten by module<br>
   * C type : _DWORD
   */
  @CLong
  @Field(3)
  public DCAM_HDR_PARAM oFlag(final long oFlag)
  {
    this.io.setCLongField(this, 3, oFlag);
    return this;
  }

  public DCAM_HDR_PARAM(final Pointer pointer)
  {
    super(pointer);
  }
}
