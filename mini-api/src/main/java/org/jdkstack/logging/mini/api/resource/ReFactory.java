package org.jdkstack.logging.mini.api.resource;

import org.jdkstack.logging.mini.api.recorder.Recorder;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public interface ReFactory {

  /**
   * .
   *
   * <p>.
   *
   * @param key key.
   * @return boolean .
   * @author admin
   */
  boolean containsKey(String key);

  /**
   * .
   *
   * <p>.
   *
   * @param name name.
   * @return Recorder .
   * @author admin
   */
  Recorder getRecorder(String name);

  /**
   * .
   *
   * <p>.
   *
   * @param name name.
   * @param recorder recorder.
   * @author admin
   */
  void putRecorder(String name, Recorder recorder);
}
