package org.jdkstack.logging.mini.extension.recorder;

import org.jdkstack.logging.mini.api.config.RecorderConfig;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.recorder.Recorder;

/**
 * 提供所有日志的方法.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class DiagnosticLogRecorder implements Recorder {

  private final LogRecorderContext context;

  /** 一个LogRecorder有一个LogRecorderConfig配置. 有界数组阻塞队列，考虑使用全局配置和个性化配置？. */
  private RecorderConfig recorderConfig;

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param context        context.
   * @param recorderConfig recorderConfig.
   * @author admin
   */
  public DiagnosticLogRecorder(final LogRecorderContext context, final RecorderConfig recorderConfig) {
    this.context = context;
    this.recorderConfig = recorderConfig;
  }

  @Override
  public RecorderConfig getRecorderConfig() {
    return this.recorderConfig;
  }

  @Override
  public void setRecorderConfig(final RecorderConfig recorderConfig) {
    this.recorderConfig = recorderConfig;
  }

  @Override
  public void log(final String logLevel, final String datetime, final String message, final Throwable thrown) {
    this.core(logLevel, datetime, message, null, null, null, null, null, null, null, null, null, thrown);
  }

  @Override
  public final void log(final String logLevel, final String message) {
    this.core(logLevel, message, null, null, null, null, null, null, null, null, null);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1) {
    this.core(logLevel, message, arg1, null, null, null, null, null, null, null, null);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2) {
    this.core(logLevel, message, arg1, arg2, null, null, null, null, null, null, null);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3) {
    this.core(logLevel, message, arg1, arg2, arg3, null, null, null, null, null, null);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
    this.core(logLevel, message, arg1, arg2, arg3, arg4, null, null, null, null, null);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
    this.core(logLevel, message, arg1, arg2, arg3, arg4, arg5, null, null, null, null);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6) {
    this.core(logLevel, message, arg1, arg2, arg3, arg4, arg5, arg6, null, null, null);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7) {
    this.core(logLevel, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, null, null);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8) {
    this.core(logLevel, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, null);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9) {
    this.core(logLevel, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
  }

  @Override
  public final void log(final String logLevel, final String message, final Throwable thrown) {
    this.core(logLevel, null, message, null, null, null, null, null, null, null, null, null, thrown);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Throwable thrown) {
    this.core(logLevel, null, message, arg1, null, null, null, null, null, null, null, null, thrown);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Throwable thrown) {
    this.core(logLevel, null, message, arg1, arg2, null, null, null, null, null, null, null, thrown);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Throwable thrown) {
    this.core(logLevel, null, message, arg1, arg2, arg3, null, null, null, null, null, null, thrown);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Throwable thrown) {
    this.core(logLevel, null, message, arg1, arg2, arg3, arg4, null, null, null, null, null, thrown);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Throwable thrown) {
    this.core(logLevel, null, message, arg1, arg2, arg3, arg4, arg5, null, null, null, null, thrown);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Throwable thrown) {
    this.core(logLevel, null, message, arg1, arg2, arg3, arg4, arg5, arg6, null, null, null, thrown);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Throwable thrown) {
    this.core(logLevel, null, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, null, null, thrown);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Throwable thrown) {
    this.core(logLevel, null, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, null, thrown);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Throwable thrown) {
    this.core(logLevel, null, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, thrown);
  }

  @Override
  public final void process(final String logLevel, final String name, final String dateTime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Throwable thrown) {
    // 日志级别是否匹配，这个过滤器。
    if (this.context.doFilter(logLevel, recorderConfig.getMaxLevel(), recorderConfig.getMinLevel())) {
      // 使用当前线程执行生产业务。
      this.context.process(logLevel, dateTime, message, name, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, thrown);
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message  .
   * @author admin
   */
  public final void core(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9) {
    this.core(logLevel, null, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param dateTime .
   * @param message  .
   * @author admin
   */
  public final void core(final String logLevel, final String dateTime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9) {
    this.process(logLevel, this.recorderConfig.getName(), dateTime, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, null);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message  .
   * @author admin
   */
  public final void core(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Throwable thrown) {
    this.core(logLevel, null, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, thrown);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message  .
   * @param thrown   .
   * @author admin
   */
  public final void core(final String logLevel, final String dateTime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Throwable thrown) {
    this.process(logLevel, this.recorderConfig.getName(), dateTime, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, thrown);
  }
}
