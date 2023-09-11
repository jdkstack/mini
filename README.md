这是一个日志框架内核，可扩展。

1. 仅实现核心功能FileHandler，将日志数据写入文件中。
2. 没有临时对象创建(临时对象包括，日志 objects，String，char arrays, byte arrays)。
3. 将日志 objects转换成StringBuilder。然后将StringBuilder转换成ByteBuffer。最后将ByteBuffer写入文件中，这个过程不会产生任何临时对象。
4. 不依赖第三方库，仅仅依赖openJdk。
5. 仅支持输出字符串(参数)。

目录结构：

```java
mini-api (接口)
mini-core (实现)
--org.jdkstack.bean.core (bean类管理)
--org.jdkstack.logging.mini.core (日志)
--org.jdkstack.pool.core (线程池)
--org.jdkstack.ringbuffer.core (无锁队列)
mini-extension (扩展)
```

openJdk版本:

```java
java -version
openjdk version "11" 2018-09-25
OpenJDK Runtime Environment 18.9 (build 11+28)
OpenJDK 64-Bit Server VM 18.9 (build 11+28, mixed mode)
```

例子:

```java
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
```

执行main方法时，指定jvm 参数，gc日志会输出到控制台：
-Xmx32m -Xmx32m -Xlog:gc*

日志存储目录路径：
默认当前目录下\logs1694229262694\default\1694229262694.log (1694229262694是创建日志目录和文件时的时间戳)。

如果你在测试时，有任何问题可以在github上提交issues或者创建discussions。

我会认真读并回复，也欢迎各位伙伴们能帮忙点一个星星。

对于使用Jconsole, Jmc等工具观察jvm进程情况的朋友，一定要注意。

这些工具会注入jvm并创建大量的临时对象，会造成内存的升高，一定要有这个鉴别的能力。

也可以借助jmap -histo pid来观察jvm对象的数量，内存的大小。

注意，日志库本身不会发生GC，如果你的项目引入了这个日志库，项目自身GC问题，日志库无法左右。

项目自身创建的对象过多，达到jvm GC算法阈值，一定会发生GC。
