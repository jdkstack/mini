package org.jdkstack.logging.mini.core.factory;

import org.jdkstack.logging.mini.api.context.LogRecorderContextFactory;
import org.jdkstack.logging.mini.api.factory.Log;
import org.jdkstack.logging.mini.api.recorder.Recorder;

/**
 * 日志对象.
 *
 * <p>使用Recorder所有日志级别的方法.
 *
 * @author admin
 */
public class DefaultLog implements Log {

  /** Recorder日志记录器对象名. */
  public String name;
  public LogRecorderContextFactory factory;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @author admin
   */
  public DefaultLog(final String name, final LogRecorderContextFactory factory) {
    this.name = name;
    this.factory = factory;
  }

  @Override
  public void log(final String logLevel, final String datetime, final String message, final Throwable thrown) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Throwable thrown) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Throwable thrown) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Throwable thrown) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Throwable thrown) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Throwable thrown) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Throwable thrown) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Throwable thrown) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Throwable thrown) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Throwable thrown) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Throwable thrown) {
    Recorder recorder = factory.getRecorder(name);
    recorder.log(logLevel, message);
  }
}
