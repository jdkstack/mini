package org.jdkstack.logging.mini.api.lz4;

import java.nio.ByteBuffer;

public interface Lz4Encoder {

  void encode(ByteBuffer input, ByteBuffer output);
}
