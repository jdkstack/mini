package org.jdkstack.logging.mini.extension.web;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Environment {

    /**
     * Home目录
     */
    private final Path home;
    /**
     * 临时目录
     */
    private final Path tmp;
    /**
     * 日志目录
     */
    private final Path log;
    /**
     * 文件目录
     */
    private final Path app;
    /**
     * 配置目录
     */
    private final Path conf;
    /**
     * 脚本目录
     */
    private final Path bin;
    /**
     * class path)目录
     */
    private final Path lib;

    public Environment() {
        home = FileSystems.getDefault().getPath("." + File.separator).toAbsolutePath().normalize();
        tmp = home.resolve("temp");
        log = home.resolve("log");
        conf = home.resolve("conf");
        app = home.resolve("app");
        bin = home.resolve("bin");
        lib = home.resolve("lib");
    }

    public Path getHome() {
        return this.home;
    }

    public Path getTmp() {
        return this.tmp;
    }

    public Path getLog() {
        return this.log;
    }

    public Path getApp() {
        return this.app;
    }

    public Path getConf() {
        return this.conf;
    }

    public Path getBin() {
        return this.bin;
    }

    public Path getLib() {
        return this.lib;
    }
}
