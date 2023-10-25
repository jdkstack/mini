package org.jdkstack.logging.mini.extension.web.exception;

/**
 * 处理请求异常
 *
 * @author admin
 */
public final class HandleRequestException extends RuntimeException {

  public HandleRequestException() {}

  public HandleRequestException(String message) {
    super(message);
  }

  public HandleRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public HandleRequestException(Throwable cause) {
    super(cause);
  }
}
