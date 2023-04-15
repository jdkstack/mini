A lightweight, high performance, open source, application layer log service framework.

一个轻量级，高性能，开源，应用层日志服务框架。

```text
这是一个日志框架内核，可扩展，单线程模式，阻塞式生产和消费日志数据。

动机：基于以下几点原因。
1.仅实现核心功能FileHandler，将日志数据写入文件中。
2.没有临时对象创建，不会发生GC。
3.代码级别动态配置和web页面上动态配置。
4.不依赖第三方库，仅仅依赖openJdk。
5.仅支持输出字符串。
```

例子:

```java
public class Examples {
private static final Recorder LOG = LogFactory.getLog(Examples.class);
public static void main(String[] args) {
 LOG.log(Constants.INFO,"日期", "INFO {} {}{}", to(1), to(1L), to("1"));
 LOG.log(Constants.INFO, "INFO {} {}{}", to(1), to(1L), to("1"));
 LOG.log(Constants.ERROR, "ERROR {1} ", to(1D));
 LOG.log(Constants.DEBUG, 类路径, 方法名, 行号, "ERROR {1}", to(1D));
 }
}
```

动态创建Handler和Recorder:

```java
Bean recorderManagerBean = StartApplication.context().getBean("recorderManager");
Bean handlerManagerBean = StartApplication.context().getBean("handlerManager");
HandlerManager handlerManager = (HandlerManager) handlerManagerBean.getObj();
handlerManager.create(new LogHandlerOption());
RecorderManager recorderManager = (RecorderManager) recorderManagerBean.getObj();
recorderManager.create(new LogRecorderOption());
```

动态创建Filter和Formatter:

```java
Bean formatterManagerBean = StartApplication.context().getBean("formatterManager");
FormatterManager formatterManager = (FormatterManager) formatterManagerBean.getObj();
Bean filterManagerBean = StartApplication.context().getBean("filterManager");
FilterManager filterManager = (FilterManager) filterManagerBean.getObj();
```

动态创建Level:

```java
Bean levelManagerBean = StartApplication.context().getBean("levelManager");
LevelManager levelManager = (LevelManager) levelManagerBean.getObj();
```
