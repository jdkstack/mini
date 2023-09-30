package org.jdkstack.logging.mini.core.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServerEnvironment {

  /** Home目录. */
  private final Path home;

  /** 临时目录. */
  private final Path tmp;

  /** 日志目录. */
  private final Path log;

  /** 配置目录. */
  private final Path conf;

  private final String app = System.getProperty("app");

  private final String host = System.getProperty("host");

  private final String ip = System.getProperty("ip");

  private final String port = System.getProperty("port");

  private final long pid = ProcessHandle.current().pid();

  public ServerEnvironment() {
    final FileSystem fileSystem = FileSystems.getDefault();
    final String homePath = '.' + File.separator;
    this.home = fileSystem.getPath(homePath);
    this.tmp = fileSystem.getPath(homePath + "temp");
    this.createDirs(this.tmp);
    System.setProperty("java.io.tmpdir", homePath + "temp");
    this.log = fileSystem.getPath(homePath + "log");
    this.createDirs(this.log);
    this.conf = fileSystem.getPath(homePath + "conf");
    this.createDirs(this.conf);
  }

  private void createDirs(final Path path) {
    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);
      } catch (final IOException ignored) {
        //
      }
    }
  }
}
