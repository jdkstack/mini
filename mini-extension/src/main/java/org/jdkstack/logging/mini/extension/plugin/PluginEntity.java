package org.jdkstack.logging.mini.extension.plugin;

import java.net.URLClassLoader;

public class PluginEntity {

  private String pluginName;
  private URLClassLoader classLoader;
  private PluginMeta pluginMeta;
  private Plugin plugin;
  private PluginStatus pluginsStatus;

  public String getPluginName() {
    return pluginName;
  }

  public void setPluginName(String pluginName) {
    this.pluginName = pluginName;
  }

  public URLClassLoader getClassLoader() {
    return classLoader;
  }

  public void setClassLoader(URLClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  public PluginMeta getPluginMeta() {
    return pluginMeta;
  }

  public void setPluginMeta(PluginMeta pluginMeta) {
    this.pluginMeta = pluginMeta;
  }

  public Plugin getPlugin() {
    return plugin;
  }

  public void setPlugin(Plugin plugin) {
    this.plugin = plugin;
  }

  public PluginStatus getPluginsStatus() {
    return pluginsStatus;
  }

  public void setPluginsStatus(PluginStatus pluginsStatus) {
    this.pluginsStatus = pluginsStatus;
  }
}
