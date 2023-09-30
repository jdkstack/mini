/**
 * 日志内核的上下文对象.
 *
 * <p>日志内核初始化后，会创建一个上下文对象(提供业务方法).
 *
 * <p>初始化Filter,Recorder,Handler,Formatter,Queue等.
 *
 * <p>上下文对象提供各种业务方法,包括向队列中放入数据，从队列中取出数据.
 *
 * @author admin
 */
package org.jdkstack.logging.mini.core.context;
