package org.jdkstack.logging.mini.extension.web.data;

import java.lang.reflect.Method;

/**
 * 元数据方法
 *
 * @author admin
 */
public class MethodMetaData {

  private Method method;
  private String fullPath;
  private String basePath;

  public MethodMetaData(final Method method, final String fullPath, final String basePath) {
    this.method = method;
    this.fullPath = fullPath;
    this.basePath = basePath;
  }

  public MethodMetaData() {
  }

  public String getBasePath() {
    return this.basePath;
  }

  public void setBasePath(final String basePath) {
    this.basePath = basePath;
  }

  public String getFullPath() {
    return this.fullPath;
  }

  public void setFullPath(final String fullPath) {
    this.fullPath = fullPath;
  }

  public Method getMethod() {
    return this.method;
  }

  public void setMethod(final Method method) {
    this.method = method;
  }
}
