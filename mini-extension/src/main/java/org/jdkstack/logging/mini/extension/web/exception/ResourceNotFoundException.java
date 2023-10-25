package org.jdkstack.logging.mini.extension.web.exception;

/**
 * 资源没有发现异常
 *
 * @author admin
 */
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException() {}

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResourceNotFoundException(Throwable cause) {
    super(cause);
  }
}
