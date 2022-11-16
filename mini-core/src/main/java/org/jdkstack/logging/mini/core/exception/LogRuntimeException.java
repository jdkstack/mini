package org.jdkstack.logging.mini.core.exception;

/**
 * 1.
 *
 * <p>.
 *
 * @author admin
 */
public class LogRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @author admin
   */
  public LogRuntimeException(final String message) {
    super(message);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param cause .
   * @author admin
   */
  public LogRuntimeException(final Throwable cause) {
    super(cause);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @param cause   .
   * @author admin
   */
  public LogRuntimeException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
