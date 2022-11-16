/**
 * 初始化日志框架核心组件.
 *
 * <p>初始化顺序在StartApplication类的注解@ComponentScan中定义.
 *
 * <pre>
 *   必须提前初始化的组件如下：
 *   1.handler输出日志。
 *   2.filter过滤日志。
 *   3.formatter格式化日志。
 *   4.level初始化日志级别。
 *   5.recorder初始化所有的日志对象。
 * </pre>
 *
 * @author admin
 */
package org.jdkstack.logging.mini.core.resource;
