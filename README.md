这是一个日志框架内核，可扩展。

核心功能：

1. 仅支持RandomAccessFile，MappedByteBuffer写日志文件。
2. 仅依赖高性能开源库com.lmax.disruptor。
3. 日志消息支持输出字符串(最大9个参数和1个异常参数)。
4. 日志消息支持json text和plain text格式化。
5. 支持无垃圾(GarbageCollection-Free)，无临时对象创建(临时对象包括objects, String, char arrays, byte arrays)。
6. 日志消息支持多线程生产和多线程消费。
7. 日志文件支持行数和大小来切割。
8. 支持自定义日志级别。
9. 支持不同包，不同类输出到指定日志文件。
10. 日志文件支持单条和批量写入。
11. 不支持MDC。
12. 不支持MDC。
13. 不支持异常堆栈。
14. 不支持Location。

目录结构：

```java
mini-api (接口)
mini-core (实现)
mini-extension (扩展)
mini-examples (例子)
mini-jmh (测试)
mini-distribution(分发包)
```

执行main方法时，指定jvm 参数，gc日志会输出到控制台：

使用G1GC：

-Xmx32m -Xmx32m -Xlog:gc*

日志存储目录路径：

默认当前目录下\logs\default\default[0-15].log。

# **注意:**

**日志库本身不会产生临时对象，当GC线程执行回收临时对象时，收集很快，最大限度的减轻GC的负担。**

**因为没有临时对象创建，即使GC线程一直处于运行中，但是因为不满足回收条件，并不会触发GC回收的动作。**

**GC-F(GarbageCollection-Free)，无GC的意思是没有可以回收的垃圾，并不是让GC线程停止，GC线程一直在运行中。**

**mini11运行时：OpenJDK 11和GraalVM for OpenJDK 11**

**mini21运行时：OpenJDK 21和GraalVM for OpenJDK 21**

**支持GraalVm native image**
