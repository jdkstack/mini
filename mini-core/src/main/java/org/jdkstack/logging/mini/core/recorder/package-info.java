/**
 * 日志框架中的Recorder是什么（别名 Logger）.
 *
 * <p>当我们在任何一个类中调用private static final Log LOG = LogFactory.getLog(ExamplesWorker.class);。
 *
 * <p>都会创建一个Recorder，是1:1的关系。
 *
 * <pre>
 *   例如:
 *   1.在类ExamplesWorker使用日志框架。
 *      例如：private static final Log LOG = LogFactory.getLog(ExamplesWorker.class);
 *   2.调用LogFactory.getLog()方法，内部会创建一个对象，这个对象就是Recorder。
 *      (1)Recorder包含大量的日志方法，包括INFO，ERROR......
 *      (2)参数"ExamplesWorker.class"是为了给Recorder一个名字，理论上这个名字可以是任何的字符串。
 *      (3)这个名字是便于观察哪个类输出的日志,哪个类调用了INFO,ERROR方法，使用ExamplesWorker.class是推荐的。
 * </pre>
 *
 * @author admin
 */
package org.jdkstack.logging.mini.core.recorder;
