package dcamapi;

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * <i>native declaration : lib\dcam\inc\dcamapi.h:489</i><br>
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
public class DCAM_METADATAHDR extends StructObject
{
  public DCAM_METADATAHDR()
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
  public DCAM_METADATAHDR size(final long size)
  {
    this.io.setCLongField(this, 0, size);
    return this;
  }

  // / [in] DCAM_METADATAKIND
  @CLong
  @Field(1)
  public long iKind()
  {
    return this.io.getCLongField(this, 1);
  }

  // / [in] DCAM_METADATAKIND
  @CLong
  @Field(1)
  public DCAM_METADATAHDR iKind(final long iKind)
  {
    this.io.setCLongField(this, 1, iKind);
    return this;
  }

  // / [in] 0 reserved
  @CLong
  @Field(2)
  public long option()
  {
    return this.io.getCLongField(this, 2);
  }

  // / [in] 0 reserved
  @CLong
  @Field(2)
  public DCAM_METADATAHDR option(final long option)
  {
    this.io.setCLongField(this, 2, option);
    return this;
  }

  // / [in] start frame index
  @CLong
  @Field(3)
  public long iFrame()
  {
    return this.io.getCLongField(this, 3);
  }

  // / [in] start frame index
  @CLong
  @Field(3)
  public DCAM_METADATAHDR iFrame(final long iFrame)
  {
    this.io.setCLongField(this, 3, iFrame);
    return this;
  }

  public DCAM_METADATAHDR(final Pointer pointer)
  {
    super(pointer);
  }
}
