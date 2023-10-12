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
mini-extension (扩展)
mini-examples (例子)
mini-jmh (测试)
```

执行main方法时，指定jvm 参数，gc日志会输出到控制台：

使用G1GC：

-Xmx32m -Xmx32m -Xlog:gc*

日志存储目录路径：
默认当前目录下\logs\default\default[0-15].log。

如果你在测试时，有任何不理解的地方或者有任何好的建议，有任何问题可以在github上提交issues或者创建discussions。

我会认真读并回复，欢迎一起讨论，也欢迎各位伙伴们能帮忙点一个星星，我会一直维护下去，不会放弃。

对于使用Jconsole, Jmc等工具观察jvm进程情况的朋友，一定要注意。

这些工具会注入jvm并创建大量的临时对象，会造成内存的升高，一定要有这个鉴别的能力。

也可以借助jmap -histo pid来观察jvm对象的数量，内存的大小。

注意:

日志库本身不会产生临时对象，当GC线程执行回收临时对象时，收集很快，最大限度的减轻GC的负担。

因为没有临时对象创建，即使GC线程一直处于运行中，但是因为不满足回收条件，并不会触发GC回收的动作。

GC-F(GarbageCollection-free)，无GC的意思是没有可以回收的垃圾，并不是让GC线程停止，GC线程一直在运行中。
