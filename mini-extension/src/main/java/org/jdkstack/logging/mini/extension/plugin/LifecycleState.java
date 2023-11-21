package org.jdkstack.logging.mini.extension.plugin;

public enum LifecycleState {
  /**
   * 新
   */
  NEW(false, "new"),
  /**
   * 初始化
   */
  INITIALIZING(false, "before_init"),
  /**
   * 初始化
   */
  INITIALIZED(false, "after_init"), STARTING_PREP(false, "before_start"), STARTING(true, "start"), STARTED(true, "after_start"), STOPPING_PREP(true, "before_stop"), STOPPING(false, "stop"), STOPPED(false, "after_stop"), DESTROYING(false, "before_destroy"), DESTROYED(false, "after_destroy"), CLOSED(false, "closed"), FAILED(false, null);

  private final boolean available;
  private final String lifecycleEvent;

  private LifecycleState(boolean available, String lifecycleEvent) {
    this.available = available;
    this.lifecycleEvent = lifecycleEvent;
  }

  public boolean isAvailable() {
    return available;
  }

  public String getLifecycleEvent() {
    return lifecycleEvent;
  }
}
