package org.jdkstack.logging.mini.extension.plugin;

public abstract class AbstractPlugin implements Plugin {
  private String name;
  private String description;

  public AbstractPlugin(String name, String description) {
    this.name = name;
    this.description = description;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public String description() {
    return description;
  }
}
