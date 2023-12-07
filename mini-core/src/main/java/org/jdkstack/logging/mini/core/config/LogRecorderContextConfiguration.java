package org.jdkstack.logging.mini.core.config;

import java.util.concurrent.atomic.AtomicReference;
import org.jdkstack.logging.mini.api.config.ContextConfiguration;

public class LogRecorderContextConfiguration implements ContextConfiguration {

  private int ringBufferSize = 4096;
  private int consumers = 4;
  private AtomicReference<String> state = new AtomicReference<>("asynchronous");

  @Override
  public String getState() {
    return this.state.get();
  }

  @Override
  public void setState(final String state) {
    this.state.set(state);
  }

  @Override
  public int getRingBufferSize() {
    return this.ringBufferSize;
  }

  @Override
  public void setRingBufferSize(final int ringBufferSize) {
    this.ringBufferSize = ringBufferSize;
  }

  @Override
  public int getConsumers() {
    return this.consumers;
  }

  @Override
  public void setConsumers(final int consumers) {
    this.consumers = consumers;
  }
}
