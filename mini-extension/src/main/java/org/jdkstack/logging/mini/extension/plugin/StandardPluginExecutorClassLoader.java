package org.jdkstack.logging.mini.extension.plugin;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 标准插件executor类装入器
 *
 * @author admin
 */
public class StandardPluginExecutorClassLoader extends URLClassLoader {

  private String classLoaderName;

  /**
   * 标准插件executor类装入器
   *
   * @param classLoaderName 类装入器的名字
   * @param urls            url
   * @param parent          父
   */
  public StandardPluginExecutorClassLoader(String classLoaderName, URL[] urls, ClassLoader parent) {
    super(urls, parent);
    this.classLoaderName = classLoaderName;
  }
}
