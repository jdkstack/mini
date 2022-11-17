package org.jdkstack.jdkbean.api.factory;


import org.jdkstack.jdkbean.api.bean.Bean;

public interface Factory {

  void addBean(String name, Bean bean);

  Bean getBean(String name);

  Object getObject(String name);
}
