package org.jdkstack.logging.mini.core.option;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public class InternalOption extends LogHandlerOption {

  /** 随机一个目录. */
  private String directory = "logs" + System.currentTimeMillis();
  /** . */
  private String prefix = Constants.DEFAULT;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public InternalOption() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String getDirectory() {
    return this.directory;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param directory .
   * @author admin
   */
  @Override
  public final void setDirectory(final String directory) {
    this.directory = directory;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String getPrefix() {
    return this.prefix;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param prefix .
   * @author admin
   */
  @Override
  public final void setPrefix(final String prefix) {
    this.prefix = prefix;
  }
}
