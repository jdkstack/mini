This is a logging framework kernel that is scalable.

Core features:

01. Only RandomAccess File and MappedByteBuffer are supported for writing log files.
02. The high-performance open-source library com.lmax.disruptor.
03. Log messages support 9 parameters and 1 exception parameter.
04. Log messages support JSON text and plain text.
05. GarbageCollection-Free,not create temporary object objects, String, char arrays, byte arrays.
06. Log messages support multi-threaded produce and consume.
07. Split the log file based on the number of lines and file size.
08. Support custom log levels.
09. Support different packages and classes to output to specified log files.
10. Log files support single flush and batch flush.
11. Support elegant offline.
12. Not support MDC.
13. Not support NDC。
14. Not support exception stack。
15. ~~Not support location。~~
16. Not support web manage。
17. Not support distributed。
18. Not support storage backend to store configuration information。
19. Only handle event logs. Only application logs(diagnostic logs and audit logs).

Directory structure:

```java
mini-api (interface)
mini-core (implements)
mini-extension (extension)
mini-examples (examples)
mini-jmh (jmh)
mini-distribution(distribution)
```

Execute the main method, specify the JVM parameter, and the GC log will be output to the console:

Use G1GC：

-Xmx32m -Xms32m -Xlog:gc* -XX:+UseG1GC

Log storage directory path:

Default to current directory\logs\default\default[0-15].log。

# **Attention:**

**The log library itself does not generate temporary objects. When the GC thread performs the collection of temporary objects, it collects them quickly, minimizing the burden on the GC.**

**Because there is no temporary object creation, even if the GC thread is always running, the GC recycling action will not be triggered because the recycling conditions are not met.**

**GC-F(GarbageCollection-Free),The meaning of no GC is that there is no garbage that can be collected, and it does not mean that the GC thread stops running.**
