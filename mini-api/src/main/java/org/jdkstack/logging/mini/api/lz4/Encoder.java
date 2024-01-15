package org.jdkstack.logging.mini.api.lz4;

import java.nio.ByteBuffer;

public interface Encoder {

  void encode(ByteBuffer input, ByteBuffer output);
}
