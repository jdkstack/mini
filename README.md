A lightweight, high performance, open source, application layer log service framework.

一个轻量级，高性能，开源，应用层日志服务框架。

```text
这是一个日志框架内核，可扩展。

现有开源日志框架功能臃肿，代码复杂，BUG非常多，漏洞非常多。

此日志框架使用单线程模式，阻塞式生产和消费日志数据。

每产生一条日志写入文件（每秒写入15W条，每条0.5kb），每100条日志写入文件（每秒写入30W条，每条0.5kb）。

多线程模式是单线程模式的2倍性能（每秒写入30W和60W，暂时不支持）。

在生产能力足够的情况下，理论上能达到磁盘IO的最大写入峰值。

动机：基于以下几点原因。
1.日志框架代码轻量，仅实现核心功能，目前不到5000行代码，预期最多10000行代码。
2.日志框架没有临时对象被创建，不会发生GC。
3.日志框架非核心功能可以基于日志核心来扩展。
4.日志框架业务逻辑非常简单，任何人都可以看懂，可以贡献。
5.日志框架实现代码级别上的动态配置（Level，Filter，Formatter......）。
6.日志框架提供web服务这个非核心功能（页面上的动态配置）。
7.日志框架仅提供将日志数据写入文件中（其他方式例如kafka，控制台，mysql可以扩展实现）。
8.源码中SonarLint需要修复80%。
9.源码中CheckStyle Google需要修复80%。
10.源码中CheckStyle Sun需要修复80%。
11.源码中IDEA INSPECTION需要修复80%。
12.只依赖openjdk，没有第三方库。
13.实现了日期时间工具类，环形队列，Bean管理以及依赖注入。
14.日志框架只支持字符串的输出，不支持对象等。
15.此外，最重要的一点是，日志框架没有一行垃圾代码，哪怕是一个标点符号（如果有，会被删除），不间断的优化代码质量，直到完美。
```

**当前存在的问题：
1.写文件切换时，切换逻辑有一点问题(时间非线性增长)。**

例子:

```java
public class Examples {
private static final Recorder LOG = LogFactory.getLog(Examples.class);
public static void main(String[] args) {
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
