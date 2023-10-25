package org.jdkstack.logging.mini.extension.plugin.rest;

import org.jdkstack.logging.mini.extension.plugin.AbstractPlugin;

public abstract class AbstractRestPlugin extends AbstractPlugin implements RestPlugin {

  public AbstractRestPlugin(String name, String description) {
    super(name, description);
  }
}
