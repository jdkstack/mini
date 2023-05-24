package org.jdkstack.logging.mini.core.codec;

/**
 * This is a class description.
 *
 * <p>.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public final class Constants {

  /** . */
  public static final int N10 = 10;
  /** . */
  public static final int N8 = 8;
  /** . */
  public static final int N16 = 16;
  /** . */
  public static final int N32 = 32;
  /** 8KB. */
  public static final int SOURCEN8 = N8 << N10;
  /** 16KB. */
  public static final int SOURCEN16 = N16 << N10;
  /** 32KB. */
  public static final int SOURCE = N32 << N10;
  /** 32KB. */
  public static final int DESTINATION = N32 << N10;

  private Constants() {
    //
  }
}
