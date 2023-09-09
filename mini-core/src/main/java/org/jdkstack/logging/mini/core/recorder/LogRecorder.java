package org.jdkstack.logging.mini.core.recorder;

import java.util.HashMap;
import java.util.Map;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.level.Level;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.api.resource.HaFactory;
import org.jdkstack.logging.mini.api.resource.LeFactory;
import org.jdkstack.logging.mini.core.StartApplication;
import org.jdkstack.logging.mini.core.pool.StringBuilderPool;

/**
 * 提供所有日志的方法.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class LogRecorder implements Recorder {

  /** Recorder名字. */
  private final String name;
    /**
     * Recorder类别 .
     */
    private final String type;
    /**
     * 日志级别处理器 .
     */
    private final Map<String, String> handlers = new HashMap<>(16);
    /**
     * Recorder可以处理最小的日志级别.
     */
    private Level minLevel;
    /**
     * Recorder可以处理最大的日志级别.
     */
    private Level maxLevel;

    /**
     * .
     *
     * <p>Another description after blank line.
     *
     * @param name name.
     * @param type type.
     * @author admin
     */
    public LogRecorder(final String name, final String type) {
        this.name = name;
        this.type = type;
    }

    private static void extracted(StringBuilder sb, Object arg1) {
        if (arg1 instanceof Integer) {
            sb.append((int) arg1);
        } else if (arg1 instanceof Short) {
            sb.append((short) arg1);
        } else if (arg1 instanceof String) {
            sb.append((String) arg1);
        } else if (arg1 instanceof Long) {
            sb.append((long) arg1);
        } else if (arg1 instanceof Character) {
            sb.append((char) arg1);
        } else if (arg1 instanceof Double) {
            sb.append((double) arg1);
        } else if (arg1 instanceof Float) {
            sb.append((float) arg1);
        } else if (arg1 instanceof Boolean) {
            sb.append((boolean) arg1);
        }
    }

    public static void main(String[] args) {
        LogRecorder logRecorder = new LogRecorder("", "");
        Object arg1 = 123;
        for (; ; ) {

            //logRecorder.trace("xxxxxx",22334 , "asdasdasd");
            logRecorder.log("xxxxx", "1", "2", 'c', "4", 5, 6, 7, 8, 9, 10, 11, null);
        }
    }

    /**
     * .
     *
     * <p>Another description after blank line.
     *
     * @param logLevels .
     * @return boolean .
     * @author admin
     */
    @Override
    public final boolean doFilter(final String logLevels) {
        final LeFactory info = StartApplication.getBean("levelFactory", LeFactory.class);
        return info.doFilter(logLevels, this.maxLevel, this.minLevel);
    }

    /**
     * .
     *
     * <p>Another description after blank line.
     *
     * @return String .
     * @author admin
     */
    @Override
    public final String getName() {
        return this.name;
    }

    /**
     * .
     *
     * <p>Another description after blank line.
     *
     * @return String .
     * @author admin
     */
    @Override
    public final String getType() {
        return this.type;
    }

    /**
     * .
     *
     * <p>Another description after blank line.
     *
     * @param handler .
     * @author admin
     */
    @Override
    public final void removeHandler(final String handler) {
        this.handlers.remove(handler);
    }

    /**
     * .
     *
     * <p>Another description after blank line.
     *
     * @param handler .
     * @author admin
     */
    @Override
    public final void addHandlers(final String key, final String handler) {
        this.handlers.put(key, handler);
    }

    /**
     * .
     *
     * <p>Another description after blank line.
     *
     * @param key .
     * @author admin
     */
    @Override
    public final String getHandler(final String key) {
        return this.handlers.get(key);
    }

    @Override
    public final Level getMinLevel() {
        return this.minLevel;
    }

    @Override
    public final void setMinLevel(final Level minLevel) {
        this.minLevel = minLevel;
    }

    @Override
    public final Level getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public final void setMaxLevel(final Level maxLevel) {
        this.maxLevel = maxLevel;
    }

    @Override
    public void log(String logLevel, String datetime, String message, Throwable thrown) {

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
        this.core(logLevel, null, message,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    }

    /**
     * This is a method description.
     *
     * <p>Another description after blank line.
     *
     * @param logLevel .
     * @param datetime .
     * @param message  .
     * @author admin
     */
    public final void core(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9) {
        // 日志级别是否匹配.
        if (this.doFilter(logLevel)) {
            final HaFactory info = StartApplication.getBean("handlerFactory", HaFactory.class);
            // 日志级别.
            String handler = this.handlers.get(logLevel);
            if (null == handler) {
                // 自己.
                handler = this.handlers.get(this.name);
            }
            Handler handler1 = info.getHandler(handler);
            handler1.process(logLevel, this.name, datetime, message,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,null);
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
    public final void core(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Throwable thrown) {
        this.core(logLevel, null, message,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9, thrown);
    }

    /**
     * This is a method description.
     *
     * <p>Another description after blank line.
     *
     * @param logLevel .
     * @param datetime .
     * @param message  .
     * @author admin
     */
    public final void core(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Throwable thrown) {
        // 日志级别是否匹配.
        if (this.doFilter(logLevel)) {
            final HaFactory info = StartApplication.getBean("handlerFactory", HaFactory.class);
            // 日志级别.
            String handler = this.handlers.get(logLevel);
            if (null == handler) {
                // 自己.
                handler = this.handlers.get(this.name);
            }
            Handler handler1 = info.getHandler(handler);
            handler1.process(logLevel, this.name, datetime, message,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9, thrown);
        }
    }

    @Override
    public final void log(final String logLevel, final String message) {

    }

    @Override
    public final void log(final String logLevel, final String message, final Object arg1) {
        this.core(logLevel,message,arg1,null,null,null,null,null,null,null,null);
    }

    @Override
    public final void log(final String logLevel, final String message, final Object arg1, final Object arg2) {
        this.core(logLevel,message,arg1,arg2,null,null,null,null,null,null,null);
    }

    @Override
    public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3) {
        this.core(logLevel,message,arg1,arg2,arg3,null,null,null,null,null,null);
    }

    @Override
    public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
        this.core(logLevel,message,arg1,arg2,arg3,arg4,null,null,null,null,null);
    }

    @Override
    public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
        this.core(logLevel,message,arg1,arg2,arg3,arg4,arg5,null,null,null,null);
    }

    @Override
    public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6) {
        this.core(logLevel,message,arg1,arg2,arg3,arg4,arg5,arg6,null,null,null);
    }

    @Override
    public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7) {
        this.core(logLevel,message,arg1,arg2,arg3,arg4,arg5,arg6,arg7,null,null);
    }

    @Override
    public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8) {
        this.core(logLevel,message,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,null);
    }

    @Override
    public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9) {
        this.core(logLevel,message,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    }

    @Override
    public final void log(final String logLevel, final String message, final Throwable thrown) {

    }

    @Override
    public final void log(final String logLevel, final String message, final Object arg1, final Throwable thrown) {

    }
 
    @Override
  public final void log(
            final String logLevel,
            final String message,
            final Object arg1,
            final Object arg2,
            final Throwable thrown) {
        //
  }

    @Override
    public final void log(final String logLevel, final String message, final Object arg1, final Object arg2, final Object arg3, final Throwable thrown) {

    }

    @Override
  public final void log(
            final String logLevel,
            final String message,
            final Object arg1,
            final Object arg2,
            final Object arg3,
            final Object arg4,
            final Throwable thrown) {
    //
  }

    @Override
    public final void log(
            final String logLevel,
            final String message,
            final Object arg1,
            final Object arg2,
            final Object arg3,
            final Object arg4,
            final Object arg5,
            final Throwable thrown) {
        //
    }

    @Override
    public final void log(
            final String logLevel,
            final String message,
            final Object arg1,
            final Object arg2,
            final Object arg3,
            final Object arg4,
            final Object arg5,
            final Object arg6,
            final Throwable thrown) {
        //
    }

    @Override
    public final void log(
            final String logLevel,
            final String message,
            final Object arg1,
            final Object arg2,
            final Object arg3,
            final Object arg4,
            final Object arg5,
            final Object arg6,
            final Object arg7,
            final Throwable thrown) {
        //
    }

    @Override
    public final void log(
            final String logLevel,
            final String message,
            final Object arg1,
            final Object arg2,
            final Object arg3,
            final Object arg4,
            final Object arg5,
            final Object arg6,
            final Object arg7,
            final Object arg8,
            final Throwable thrown) {
        //
    }

    @Override
    public final void log(
            final String logLevel,
            final String message,
            final Object arg1,
            final Object arg2,
            final Object arg3,
            final Object arg4,
            final Object arg5,
            final Object arg6,
            final Object arg7,
            final Object arg8,
            final Object arg9,
            final Throwable thrown) {
        //
    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Throwable thrown) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Throwable thrown) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3, final Throwable thrown) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Throwable thrown) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Throwable thrown) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Throwable thrown) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Throwable thrown) {

    }

    @Override
    public final void log(final String logLevel, final String datetime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Throwable thrown) {

        StringBuilder poll = StringBuilderPool.poll();
        poll.append(logLevel);
        poll.append(datetime);
        poll.append(message);
        extracted(poll, arg1);
        extracted(poll, arg2);
        extracted(poll, arg3);
        extracted(poll, arg4);
        extracted(poll, arg5);
        extracted(poll, arg6);
        extracted(poll, arg7);
        extracted(poll, arg8);
        extracted(poll, arg9);
    }

    public final void trace(final String message, final Object... args) {
        //

    }
}
