package org.jdkstack.jdkbean.api.bean;

public interface Bean {

  boolean isSingleton();

  void setSingleton(boolean singleton);

  Object getObj();

  void setObj(Object obj);

  Class<?> getClassObj();

  void setClassObj(Class<?> classObj);

  String getName();

  void setName(String name);
}
