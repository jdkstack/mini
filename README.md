A lightweight, high performance, open source, application layer log service framework.

一个轻量级，高性能，开源，应用层日志服务框架。

```text
这是一个日志框架内核，可扩展。

动机：基于以下几点原因。
1.仅实现核心功能FileHandler，将日志数据写入文件中。
2.没有临时对象创建，不会发生GC。
3.代码级别动态配置。
4.不依赖第三方库，仅仅依赖openJdk。
5.仅支持输出字符串(参数)。
6.重新开发了无锁队列(demo),RingBuffer队列(demo),线程池(demo),datetime api(demo)。
7.其他。
```

例子:

```java
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.core.factory.LogFactory;
import org.jdkstack.logging.mini.core.level.Constants;

public class Examples {
    private static final Recorder LOG = LogFactory.getLog(Examples.class);

    public static void main(String[] args) {
        for (; ; ) {
            LOG.log(Constants.FATAL, "测试1{}测试2{}测试3{}测试4{}测试5{}测试6{}测试7{}测试8{}测试9{}.", 1L, "2", '3', 4D, "5", '6', 7F, "8", '9');
        }
    }
}
```
