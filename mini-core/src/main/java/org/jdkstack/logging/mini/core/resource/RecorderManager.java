package org.jdkstack.logging.mini.core.resource;

import org.jdkstack.bean.core.annotation.Component;
import org.jdkstack.bean.core.annotation.ConstructorResource;
import org.jdkstack.logging.mini.api.option.RecorderOption;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.api.resource.LeFactory;
import org.jdkstack.logging.mini.api.resource.ReFactory;
import org.jdkstack.logging.mini.core.option.LogRecorderOption;
import org.jdkstack.logging.mini.core.recorder.LogRecorder;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
@Component
public class RecorderManager {

  /** . */
  private final ReFactory logInfos;
  /** . */
  private final LeFactory leFactory;

  /**
   * .
   *
   * <p>.
   *
   * @param recorderFactory .
   * @param leFactory .
   * @author admin
   */
  @ConstructorResource({"recorderFactory", "levelFactory"})
  public RecorderManager(final ReFactory recorderFactory, final LeFactory leFactory) {
    this.logInfos = recorderFactory;
    this.leFactory = leFactory;
    // 初始化default Recorder.
    this.init();
  }

  private void init() {
    final RecorderOption recorderOption = new LogRecorderOption();
    this.create(recorderOption);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param recorderOption .
   * @author admin
   */
  public final void create(final RecorderOption recorderOption) {
    // 设置日志处理器, 如果没有配置,不会继承父,直接使用ROOT.
    final String handlers = recorderOption.getHandlers();
    final Recorder logRecorder =
        new LogRecorder(recorderOption.getName(), recorderOption.getType());
    final String[] splits = handlers.split(",");
    for (final String split : splits) {
      final String[] split1 = split.split(":");
      if (Constants.N2 == split1.length) {
        final String levelName = split1[0];
        final String handlerName = split1[1];
        logRecorder.addHandlers(levelName, handlerName);
      } else {
        logRecorder.addHandlers(recorderOption.getName(), split);
      }
    }
    // 设置日志级别.  // 如果没有配置,不会继承父,直接使用ROOT.
    final String minLevel = recorderOption.getMinLevel();
    // 设置日志级别.  // 如果没有配置,不会继承父,直接使用ROOT.
    final String maxLevel = recorderOption.getMaxLevel();
    logRecorder.setMinLevel(this.leFactory.findLevel(minLevel));
    logRecorder.setMaxLevel(this.leFactory.findLevel(maxLevel));
    this.logInfos.putRecorder(recorderOption.getName(), logRecorder);
  }
}
