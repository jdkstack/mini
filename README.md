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

注意：
开发这个库的原因是为了学习和讨论，不是为了炫耀。目前代码质量很烂，也有很多BUG，大家先不要纠结代码质量和BUG。
还有，为什么代码要这么写，有非常多的简便写法（简便的写法无法做到无GC的要求）。
很多写法都是经过我多次测试后才决定的，为了达到真正的无jvm GC的要求，费了很多脑细胞。
欢迎各位感兴趣的朋友，欢迎各位的贡献，任何贡献都需要，包括提供一个命名，提供一个建议。

如果你在测试时，有任何问题可以在github上提交issues或者创建一个discussions。
我看到后会认真读取每一条并回复。也欢迎各位伙伴们能帮忙点一个星星。

对于使用Jconsole, Jmc等工具观察jvm进程情况的朋友，一定要注意。
这些工具会注入jvm并创建大量的临时对象，会造成内存的升高，一定要有这个鉴别的能力。
也可以借助jmap -histo pid来观察jvm对象的数量，内存的大小。
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
