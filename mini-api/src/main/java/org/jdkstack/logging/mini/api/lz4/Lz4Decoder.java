package org.jdkstack.logging.mini.api.lz4;

import java.nio.ByteBuffer;

public interface Lz4Decoder {

  void decode(ByteBuffer input, ByteBuffer output);
}
