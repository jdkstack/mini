package org.jdkstack.pool;

public class ExecutionException extends Exception {
  private static final long serialVersionUID = 7830266012832686185L;

  /**
   * Constructs an {@code ExecutionException} with no detail message. The cause is not initialized,
   * and may subsequently be initialized by a call to {@link #initCause(Throwable) initCause}.
   */
  protected ExecutionException() {}

  /**
   * Constructs an {@code ExecutionException} with the specified detail message. The cause is not
   * initialized, and may subsequently be initialized by a call to {@link #initCause(Throwable)
   * initCause}.
   *
   * @param message the detail message
   */
  protected ExecutionException(String message) {
    super(message);
  }

  /**
   * Constructs an {@code ExecutionException} with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method)
   */
  public ExecutionException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs an {@code ExecutionException} with the specified cause. The detail message is set to
   * {@code (cause == null ? null : cause.toString())} (which typically contains the class and
   * detail message of {@code cause}).
   *
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method)
   */
  public ExecutionException(Throwable cause) {
    super(cause);
  }
}
