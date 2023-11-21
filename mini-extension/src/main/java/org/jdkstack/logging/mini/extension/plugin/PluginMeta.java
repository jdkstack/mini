package org.jdkstack.logging.mini.extension.plugin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PluginMeta {

  public static final String PLUGINPROPERTIES = "plugin-descriptor.properties";
  public static final String PLUGINPOLICY = "plugin-security.policy";
  private String name;
  private String description;
  private String version;
  private String className;
  private int type;

  public PluginMeta(String name, String description, String version, String className, int type) {
    this.name = name;
    this.description = description;
    this.version = version;
    this.className = className;
    this.type = type;
  }

  public static PluginMeta getPluginMeta(Path pluginDirectory) throws IOException {
    String pluginDirName = pluginDirectory.getFileName().toString();
    Path pluginDescriptorFile = pluginDirectory.resolve(PLUGINPROPERTIES);
    Properties props = new Properties();
    props.load(Files.newBufferedReader(pluginDescriptorFile, StandardCharsets.UTF_8));
    String description = props.getProperty("plugin.description", "");
    String version = props.getProperty("plugin.version", "");
    int type = Integer.parseInt(props.getProperty("plugin.type", "1"));
    String javaVersion = props.getProperty("java.version", "11");
    assert "11".equals(javaVersion) : "jdk 版本错误,必须是jdk11";
    String className = props.getProperty("plugin.className", "");
    return new PluginMeta(pluginDirName, description, version, className, type);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
}
