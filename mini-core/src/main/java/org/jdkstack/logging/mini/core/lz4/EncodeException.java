package org.jdkstack.logging.mini.core.lz4;

public class EncodeException extends RuntimeException {

  private final long offset;

  public EncodeException(long offset) {
    this(offset, "Malformed input");
  }

  public EncodeException(long offset, String reason) {
    super(reason + ": offset=" + offset);
    this.offset = offset;
  }

  public long getOffset() {
    return offset;
  }
}
