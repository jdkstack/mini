package org.jdkstack.logging.mini.api.lifecycle;

/**
 * 生命周期 不应在接口中定义常量
 *
 * @author admin
 */
public interface Lifecycle {

  LifecycleState getState();

  void setState(LifecycleState state);
}
