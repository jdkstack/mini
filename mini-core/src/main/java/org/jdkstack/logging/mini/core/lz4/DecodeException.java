package org.jdkstack.logging.mini.core.lz4;

public class DecodeException extends RuntimeException {

  private final long offset;

  public DecodeException(long offset) {
    this(offset, "Malformed input");
  }

  public DecodeException(long offset, String reason) {
    super(reason + ": offset=" + offset);
    this.offset = offset;
  }

  public long getOffset() {
    return offset;
  }
}
