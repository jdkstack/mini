package org.jdkstack.logging.mini.core.resource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdkstack.bean.core.annotation.Component;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.api.resource.ReFactory;
import org.jdkstack.logging.mini.core.option.Constants;
import org.jdkstack.logging.mini.core.recorder.LogRecorder;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
@Component
public class RecorderFactory implements ReFactory {

  /** . */
  private final Map<String, Recorder> recorders = new ConcurrentHashMap<>(1000);

  @Override
  public final boolean containsKey(final String key) {
    return this.recorders.containsKey(key);
  }

  @Override
  public final Recorder getRecorder(final String name) {
    // 全限定名配置.
    Recorder recorder = this.recorders.get(name);
    // 默认配置.
    if (null == recorder) {
      final Recorder root = this.recorders.get(Constants.DEFAULT);
      recorder = this.create(name, root);
    }
    return recorder;
  }

  @Override
  public final void putRecorder(final String name, final Recorder recorder) {
    this.recorders.put(name, recorder);
  }

  private Recorder create(final String name, final Recorder other) {
    final Recorder recorder = new LogRecorder(name, other.getType());
    recorder.addHandlers(name, other.getHandler(other.getName()));
    recorder.setMinLevel(other.getMinLevel());
    recorder.setMaxLevel(other.getMaxLevel());
    this.putRecorder(name, recorder);
    return recorder;
  }
}
