package org.jdkstack.logging.mini.extension.web;

import org.jdkstack.logging.mini.extension.web.annotation.ComponentScan;
import org.jdkstack.logging.mini.extension.web.annotation.Filter;
import org.jdkstack.logging.mini.extension.web.application.Application;
import org.jdkstack.logging.mini.extension.web.context.ApplicationContext;
import org.jdkstack.logging.mini.extension.web.context.Context;

/**
 * 启动类,扫描要管理的Bean.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
@ComponentScan(
        value = {
                "org.jdkstack.logging.mini.extension.web.controller.BaseController",
                "org.jdkstack.logging.mini.extension.web.controller.UserController"},
        excludeFilters = {@Filter(String.class), @Filter(String.class)}
)
public final class StartApplication {

    /**
     * 上下文环境.
     */
    private static final Context CONTEXT = new ApplicationContext();

    static {
        Application.run(StartApplication.class, CONTEXT);
    }

    private StartApplication() {
        //
    }

    /**
     * This is a method description.
     *
     * <p>Another description after blank line.
     *
     * @return context.
     * @author admin
     */
    public static Context context() {
        return CONTEXT;
    }
}
