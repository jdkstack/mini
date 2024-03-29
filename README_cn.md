这是一个日志框架内核(日志库)。

核心功能：

01. 仅支持RandomAccessFile，MappedByteBuffer写日志文件。
02. 仅依赖高性能开源库com.lmax.disruptor。
03. 日志消息支持输出字符串(最大9个参数和1个异常参数)。
04. 日志消息支持json text和plain text格式化。
05. 支持无垃圾(GarbageCollection-Free)，无临时对象创建(临时对象包括objects, String, char arrays, byte arrays)。
06. 日志消息支持多线程生产和多线程消费（Log4j2消费端只支持单线程）。
07. 日志文件支持行数和大小来切割，预先创建N个文件，日志文件循环使用，日志文件不需要删除，不需要重命名，更不需要压缩。
08. 支持自定义日志级别。
09. 支持不同包，不同类输出到指定日志文件。
10. 日志文件支持单条和批量写入。
11. 支持优雅下线，当调用LogFactory.shutdown()方法时，关闭RingBuffer，停止所有消费线程，消费掉所有日志记录，新日志会同步写入日志文件。
12. 暂时不支持MDC。
13. 暂时不支持NDC。
14. 暂时不支持异常堆栈。
15. 支持Location。
16. 暂时不支持web端管理。
17. 暂时不支持分布式。
18. 暂时不支持配置持久化到后端存储。
19. 仅处理事件日志（Event logs）。 特别是应用日志（application logs），应用日志包括诊断日志（diagnostic logs ），审计日志（audit logs）。
20. 不兼容任何已经存在的日志库和日志框架，包括但不限于Log4j，Logback等。
21. 吞吐量比Log4j2高，延时比Log4j2低。
22. 支持Tomcat等web服务器。
23. 支持扩展。
24. 支持日志备份，日志压缩。

目录结构：

```java
mini-

api(接口)

mini-

core(实现)

mini-

extension(扩展)

mini-

examples(例子)

mini-

jmh(测试)

mini-

distribution(分发包)
```

# **注意:**

**日志库本身不会产生临时对象，当GC线程执行回收临时对象时，收集很快，最大限度的减轻GC的负担。**

**因为没有临时对象创建，即使GC线程一直处于运行中，但是因为不满足回收条件，并不会触发GC回收的动作。**

**GC-F(GarbageCollection-Free)，无GC的意思是没有可以回收的垃圾，并不是让GC线程停止，GC线程一直在运行中。**
