A lightweight, high performance, open source, application layer log service framework.

一个轻量级，高性能，开源，应用层日志服务框架。

```text
这是一个日志框架内核，可扩展。

动机：基于以下几点原因。
1.仅实现核心功能FileHandler，将日志数据写入文件中。
2.没有临时对象创建(临时对象包括，日志 objects，String，char arrays, byte arrays)。
  1).将日志 objects转换成StringBuilder。
  2).然后将StringBuilder转换成ByteBuffer。
  3).最后将ByteBuffer写入文件中，这个过程不会产生任何临时对象。
  4).不能使用会生成临时对象的API和方法，强制类型转换(例如toString()，getBytes()，int类型和Object类型之间的转换)。
3.代码级别动态配置。
4.不依赖第三方库，仅仅依赖openJdk。
5.仅支持输出字符串(参数)。
6.重新开发了无锁队列(demo), RingBuffer队列(demo), 线程池(demo), datetime api(demo)，
这些工具目前都是demo状态，只满足当前库的需求。
7.还有很多特性能实现，但是没有时间和精力去实现。
例如：
  1).支持web端curd(内置一个web server，提供所有接口)，动态更改日志级别，Filter，Formatter。
  2).日志输出任意格式，输出自定义context。
  3).日志无代码模式，0侵入业务代码，利用agent拦截所有方法，利用字节码编辑库动态编写日志逻辑。
  4).location位置的输出(无gc,高性能)，解决办法是每一次调用日志方法时都要绑定一个堆栈对象。
      这个堆栈对象是唯一的，与当前行绑定，用来访问堆栈信息.
  5).支持任意的文件格式，用来配置日志json,xml,yaml(日志核心不提供日志配置文件的解析，但提供对应的方法)。
  6).无锁队列, RingBuffer队列, 线程池, datetime api等工具的完善。
  7).其他。
  
注意：
写这个库的原因是为了学习和讨论，不是为了炫耀。
欢迎各位感兴趣的朋友，任何贡献都需要，包括提供一个命名，提供一个建议。
目前代码质量很烂，也有很多BUG，大家先不要纠结代码质量和BUG，可以参考style目录，是按照这个标准解决代码质量和BUG。
还有，为什么代码要这么写，有非常多的简便写法（简便的写法无法做到无GC的要求）。
很多写法都是经过我多次测试后才决定的。

如果你在测试时，有任何问题可以在github上提交issues或者创建discussions。
我会认真读并回复，也欢迎各位伙伴们能帮忙点一个星星。

对于使用Jconsole, Jmc等工具观察jvm进程情况的朋友，一定要注意。
这些工具会注入jvm并创建大量的临时对象，会造成内存的升高，一定要有这个鉴别的能力。
也可以借助jmap -histo pid来观察jvm对象的数量，内存的大小。

注意，日志库本身不会发生GC，如果你的项目引入了这个日志库，项目自身GC问题，日志库无法左右。
项目自身创建的对象过多，达到jvm GC算法阈值，一定会发生GC。

目录结构：
mini-api (接口)
mini-core (实现)
--org.jdkstack.bean.core (bean类管理)
--org.jdkstack.logging.mini.core (日志)
--org.jdkstack.pool.core (线程池)
--org.jdkstack.ringbuffer.core (无锁队列)
mini-extension (扩展)
```

例子:

```java
java -version
openjdk version "11" 2018-09-25
OpenJDK Runtime Environment 18.9 (build 11+28)
OpenJDK 64-Bit Server VM 18.9 (build 11+28, mixed mode)

import static org.jdkstack.logging.mini.core.pool.StringBuilderPool.to;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.core.factory.LogFactory;
import org.jdkstack.logging.mini.core.level.Constants;

public class Examples {
    private static final Recorder LOG = LogFactory.getLog(Examples.class);

    public static void main(String[] args) {
        for (; ; ) {
            long currentTimeMillis = System.currentTimeMillis();
            LOG.log(Constants.INFO, "测试1{}测试2{}测试3{}测试4{}测试5{}测试6{}测试7{}测试8{}测试9{}.", to(currentTimeMillis), to(2), "3", to(4D), "5", "6", to(7F), to(8), "9");
            LOG.log(Constants.FATAL, "测试1{}测试2{}测试3{}测试4{}测试5{}测试6{}测试7{}测试8{}测试9{}.", "1L", "2", "3", "4D", "5", "6", "7F", "8", "9");
        }
    }
}

执行main方法时，指定jvm 参数，gc日志会输出到控制台：
-Xmx32m -Xmx32m -Xlog:gc*

日志存储目录路径：
默认当前目录下\logs1694229262694\default\1694229262694.log (1694229262694是创建日志目录和文件时的时间戳)。
```
