package org.jdkstack.logging.mini.api.lifecycle;

public enum LifecycleState {
  /**  */
  SETUP(true, "SETUP"),
  /** 正在初始化 */
  INITIALIZING(true, "INITIALIZING"),
  /** 已经初始化 */
  INITIALIZED(true, "INITIALIZED"),
  /** 正在开始 */
  STARTING(true, "STARTING"),
  /** 已经开始 */
  STARTED(true, "STARTED"),
  /** 正在停止 */
  STOPPING(true, "STOPPING"),
  /** 已经停止 */
  STOPPED(true, "STOPPED");

  private final boolean available;
  private final String lifecycleEvent;

  LifecycleState(boolean available, String lifecycleEvent) {
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
