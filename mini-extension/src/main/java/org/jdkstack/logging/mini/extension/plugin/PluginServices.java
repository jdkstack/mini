package org.jdkstack.logging.mini.extension.plugin;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * 插件服务
 *
 * @author admin
 */
public final class PluginServices {

  /** 保存所有插件的元数据和实例 */
  private static Map<String, PluginServiceLifecycle> pluginServiceLifecycles = new HashMap<>();

  private static PluginServices instance = new PluginServices();
  private static Path pluginHomeDirectory;

  /** 插件服务 */
  private PluginServices() {
    // 读取资源国际化文件,测试
    // ResourceBundleManager rbm = ResourceBundleManager.getInstance();
    // System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + rbm.getString(PluginServices.class,
    // Locale.US, "simpleServerAuthConfig.noModules"));
  }

  /** 获得实例 */
  public static PluginServices getInstance() {
    return instance;
  }

  public static PluginServices getInstance(Path pluginHomeDir) {
    pluginHomeDirectory = pluginHomeDir;
    return instance;
  }

  /** 加载所有插件 */
  public void loadPlugins() {
    try (DirectoryStream<Path> paths = Files.newDirectoryStream(pluginHomeDirectory)) {
      for (Path pluginParentDirectory : paths) {
        // 插件的种类目录
        String pluginParentDirectoryName = pluginParentDirectory.getFileName().toString();
        // 创建一个插件的服务
        PluginServiceLifecycle pluginServiceLifecycle = new PluginServiceLifecycle(pluginParentDirectory);
        pluginServiceLifecycle.doInit();
        // 保存这个插件种类的服务
        pluginServiceLifecycles.put(pluginParentDirectoryName, pluginServiceLifecycle);
      }
    } catch (IOException e) {
      //
    }
  }

  public Map<String, PluginServiceLifecycle> getPluginServiceLifecycles() {
    return pluginServiceLifecycles;
  }
}
