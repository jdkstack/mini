package org.jdkstack.logging.mini.extension.plugin;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 插件服务生命周期
 *
 * @author admin
 */
public class PluginServiceLifecycle implements PluginService {

  /** 用来保存正常的插件信息(成功的) */
  protected static Map<String, PluginEntity> pluginEntitys = new ConcurrentHashMap<>();

  /** 用来保存不正常的插件信息(失败的) */
  protected static Map<String, PluginEntity> abnormalPluginEntitys = new ConcurrentHashMap<>();

  /** /{服务的home目录,从System中获取}/plugin/{插件的类型目录,例如:rest}/{具体插件的目录,例如:bank} */
  private Path pluginParentDir;

  /** 扫描具体插件目录下的所有的jar文件,例如:/bank/*.jar */
  private static String fileExtension = "*.jar";

  public PluginServiceLifecycle(Path pluginParentDirectory) {
    // 具体插件的上一层目录/rest
    pluginParentDir = pluginParentDirectory;
  }

  /**
   * 做初始化
   *
   * @throws IOException ioexception
   */
  protected void doInit() throws IOException {
    // 具体的插件目录/bank
    try (DirectoryStream<Path> paths = Files.newDirectoryStream(pluginParentDir)) {
      for (Path pluginTargetDir : paths) {
        // 获取/bank/*.properties插件的配置文件
        PluginMeta pluginMeta = PluginMeta.getPluginMeta(pluginTargetDir);
        // 从配置文件中得到插件名称
        String pluginName = pluginMeta.getName();
        // 从配置文件中得到插件全限定名
        String pluginClassName = pluginMeta.getClassName();
        // 获取所有的jar文件,并转换成URL格式
        List<URL> urls = jars2Urls(pluginTargetDir);
        StandardPluginExecutorClassLoader classLoader = getClassLoader(pluginName, urls);
        Class<? extends Plugin> classObj = getClass(pluginClassName, classLoader);
        if (classObj == null) {
          // throw new ServerRuntimeException("无法获取类ClassNotFoundException");
        }
        Constructor<? extends Plugin> constructor = getConstructor(classObj);
        if (constructor == null) {
          // throw new ServerRuntimeException("反射调用异常");
        }
        // 利用构造器对象,实例化具体的实例对象
        Plugin plugin = getPlugin(constructor);
        if (plugin == null) {
          // throw new ServerRuntimeException("plugin类反射异常");
        }
        // 创建一个新的实体类
        PluginEntity newPluginEntity = new PluginEntity();
        // 设置插件信息
        newPluginEntity.setPluginMeta(pluginMeta);
        // 设置classloader
        newPluginEntity.setClassLoader(classLoader);
        // 设置插件对象
        newPluginEntity.setPlugin(plugin);
        // 从配置文件中得到插件信息
        PluginEntity pluginEntity = pluginEntitys.get(pluginName);
        if (pluginEntity != null) {
          // 设置插件目前的状态是CLOSE
          newPluginEntity.setPluginsStatus(PluginStatus.CLOSE);
          // 保存到异常插件列表
          abnormalPluginEntitys.put(pluginName, pluginEntity);
        } else {
          // 设置插件目前的状态是OPEN
          newPluginEntity.setPluginsStatus(PluginStatus.OPEN);
          // 保存到正常插件列表
          pluginEntitys.put(pluginName, pluginEntity);
        }
      }
    }
  }

  /**
   * 得到类装入器
   *
   * @param pluginName 插件名称
   * @param urlsList url列表
   * @return {@link StandardPluginExecutorClassLoader}
   */
  private StandardPluginExecutorClassLoader getClassLoader(String pluginName, List<URL> urlsList) {
    // 获取自定义的类加载器,加载所有URL格式的jar文件
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    URL[] urls = urlsList.toArray(new URL[0]);
    return new StandardPluginExecutorClassLoader(pluginName, urls, contextClassLoader);
  }

  /**
   * 把插件
   *
   * @param constructor 构造函数
   * @return {@link Plugin}
   */
  private Plugin getPlugin(Constructor<? extends Plugin> constructor) {
    Plugin plugin = null;
    try {
      plugin = constructor.newInstance();
    } catch (InstantiationException e) {
      //
    } catch (IllegalAccessException e) {
      //
    } catch (InvocationTargetException e) {
      //
    }
    return plugin;
  }

  /**
   * 把构造函数
   *
   * @param classObj 类obj
   * @return {@link Constructor<? extends  Plugin >}
   */
  private Constructor<? extends Plugin> getConstructor(Class<? extends Plugin> classObj) {
    // 得到Class对象后,利用构造器进行反射,获取具体的构造器对象
    Constructor<? extends Plugin> constructor = null;
    try {
      constructor = classObj.getConstructor();
    } catch (NoSuchMethodException e) {
      //
    }
    return constructor;
  }

  /**
   * gata类
   *
   * @param pluginClassName 插件类名
   * @param specl specl
   * @return {@link Class<? extends  Plugin >}
   */
  private Class<? extends Plugin> getClass(
      String pluginClassName, StandardPluginExecutorClassLoader specl) {
    // 从加载器中获取字节码Class对象,并检查是否继承于Plugin对象
    Class<? extends Plugin> classObj = null;
    try {
      classObj = specl.loadClass(pluginClassName).asSubclass(Plugin.class);
    } catch (ClassNotFoundException e) {
      //
    }
    return classObj;
  }

  /**
   * jar -> url
   *
   * @param pluginTargetDir 插件目标dir
   * @return {@link List<URL>}* @throws IOException ioexception
   */
  private List<URL> jars2Urls(Path pluginTargetDir) throws IOException {
    List<URL> urls = new ArrayList<>();
    try (DirectoryStream<Path> paths = Files.newDirectoryStream(pluginTargetDir, fileExtension)) {
      for (Path jar : paths) {
        urls.add(jar.toRealPath().toUri().toURL());
      }
    }
    return urls;
  }

  /** 获取正常的插件列表 */
  public Map<String, PluginEntity> getPluginEntitys() {
    return pluginEntitys;
  }

  public static void main(String[] args) {
    PluginServices pluginServices = PluginServices.getInstance(null);
    pluginServices.loadPlugins();
  }
}
