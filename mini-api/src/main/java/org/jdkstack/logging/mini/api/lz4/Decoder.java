package org.jdkstack.logging.mini.api.lz4;

import java.nio.ByteBuffer;

public interface Decoder {

  void decode(ByteBuffer input, ByteBuffer output);
}
