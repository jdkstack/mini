package org.jdkstack.jdkbean.api.context;

import org.jdkstack.jdkbean.api.bean.Bean;

public interface Context {

  void scan(Class<?> application);

  void addBean(String name, Bean bean);

  Bean getBean(String name);

  Object getObject(String name);
}
