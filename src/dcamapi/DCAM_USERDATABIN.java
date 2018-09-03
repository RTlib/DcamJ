package dcamapi;

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * <i>native declaration : lib\dcam\inc\dcamapi.h:519</i><br>
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
public class DCAM_USERDATABIN extends StructObject
{
  public DCAM_USERDATABIN()
  {
    super();
  }

  // / C type : DCAM_METADATAHDR
  @Field(0)
  public DCAM_METADATAHDR hdr()
  {
    return this.io.getNativeObjectField(this, 0);
  }

  // / C type : DCAM_METADATAHDR
  @Field(0)
  public DCAM_USERDATABIN hdr(final DCAM_METADATAHDR hdr)
  {
    this.io.setNativeObjectField(this, 0, hdr);
    return this;
  }

  // / C type : void*
  @Field(1)
  public Pointer<?> bin()
  {
    return this.io.getPointerField(this, 1);
  }

  // / C type : void*
  @Field(1)
  public DCAM_USERDATABIN bin(final Pointer<?> bin)
  {
    this.io.setPointerField(this, 1, bin);
    return this;
  }

  @CLong
  @Field(2)
  public long binLen()
  {
    return this.io.getCLongField(this, 2);
  }

  @CLong
  @Field(2)
  public DCAM_USERDATABIN binLen(final long binLen)
  {
    this.io.setCLongField(this, 2, binLen);
    return this;
  }

  @CLong
  @Field(3)
  public long reserved()
  {
    return this.io.getCLongField(this, 3);
  }

  @CLong
  @Field(3)
  public DCAM_USERDATABIN reserved(final long reserved)
  {
    this.io.setCLongField(this, 3, reserved);
    return this;
  }

  public DCAM_USERDATABIN(final Pointer pointer)
  {
    super(pointer);
  }
}
