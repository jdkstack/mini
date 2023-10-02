package org.jdkstack.bean.core.context;

import java.lang.reflect.Executable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.jdkstack.bean.api.bean.Bean;
import org.jdkstack.bean.api.context.Context;
import org.jdkstack.bean.api.factory.Factory;
import org.jdkstack.bean.core.annotation.Component;
import org.jdkstack.bean.core.annotation.ComponentScan;
import org.jdkstack.bean.core.annotation.ConstructorResource;
import org.jdkstack.bean.core.bean.BeanService;
import org.jdkstack.bean.core.factory.BeanFactory;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.option.RecorderOption;
import org.jdkstack.logging.mini.core.filter.LogFilter;
import org.jdkstack.logging.mini.core.formatter.LogJsonFormatter;
import org.jdkstack.logging.mini.core.formatter.LogTextFormatter;
import org.jdkstack.logging.mini.core.handler.FileHandlerV2;
import org.jdkstack.logging.mini.core.level.Constants;
import org.jdkstack.logging.mini.core.option.LogRecorderOption;
import org.jdkstack.logging.mini.core.resource.ConfigManager;
import org.jdkstack.logging.mini.core.resource.FilterManager;
import org.jdkstack.logging.mini.core.resource.FormatterManager;
import org.jdkstack.logging.mini.core.resource.HandlerManager;
import org.jdkstack.logging.mini.core.resource.LevelManager;
import org.jdkstack.logging.mini.core.resource.RecorderManager;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class ApplicationContext implements Context {

  /** . */
  private final Factory beanFactory = new BeanFactory();

  @Override
  public final void scan(final Class<?> application) {
    try {
      // 1.扫描Bean.
      // 2.获取启动类上的注解.
      final ComponentScan componentScan = application.getAnnotation(ComponentScan.class);
      // 获取全限定名类.
      final String[] values = componentScan.value();
      // 循环每一个全限定名类.
      for (final String value : values) {
        // 反射加载类.
        final Class<?> classObj = Class.forName(value);
        this.parser(classObj);
      }
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 配置初始化.
   *
   * <p>未来需要改成从存储后端读取.
   *
   * @author admin
   */
  @Override
  public final void init() {
    Map<String, String> map = new HashMap<>(16);
    map.put("name", "default");
    map.put("level", "MAX");
    map.put("directory", "logs");
    map.put("prefix", "default");
    map.put("encoding", "UTF-8");
    map.put("type", "line");
    map.put("minLevel", "MIN");
    map.put("maxLevel", "MAX");
    map.put("formatter", "logJsonFormatter");
    map.put("filter", "logFilter");
    map.put("dateTimeFormat", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    map.put("interval", "1");
    map.put("intervalFormatter", "yyyyMMddHHmm");
    map.put("batchSize", "1");
    map.put("capacity", "1024");
    // 默认配置。
    ConfigManager configManager = (ConfigManager) getObject("configManager");
    configManager.create("default", map);
    // 默认日志级别。
    LevelManager levelManager = (LevelManager) getObject("levelManager");
    levelManager.create(Constants.MIN, Constants.MIN_VALUE);
    levelManager.create(Constants.SEVERE, Constants.SEVERE_VALUE);
    levelManager.create(Constants.FATAL, Constants.FATAL_VALUE);
    levelManager.create(Constants.ERROR, Constants.ERROR_VALUE);
    levelManager.create(Constants.WARN, Constants.WARN_VALUE);
    levelManager.create(Constants.INFO, Constants.INFO_VALUE);
    levelManager.create(Constants.DEBUG, Constants.DEBUG_VALUE);
    levelManager.create(Constants.CONFIG, Constants.CONFIG_VALUE);
    levelManager.create(Constants.FINE, Constants.FINE_VALUE);
    levelManager.create(Constants.FINER, Constants.FINER_VALUE);
    levelManager.create(Constants.FINEST, Constants.FINEST_VALUE);
    levelManager.create(Constants.TRACE, Constants.TRACE_VALUE);
    levelManager.create(Constants.MAX, Constants.MAX_VALUE);
    // 默认Filter。
    FilterManager filterManager = (FilterManager) getObject("filterManager");
    final Filter filter = new LogFilter();
    filterManager.create("logFilter", filter);
    // 默认Formatter。
    FormatterManager formatterManager = (FormatterManager) getObject("formatterManager");
    final Formatter logJsonFormatter = new LogJsonFormatter();
    formatterManager.create("logJsonFormatter", logJsonFormatter);
    final Formatter logTextFormatter = new LogTextFormatter();
    formatterManager.create("logTextFormatter", logTextFormatter);
    // 默认FileHandler。
    HandlerManager handlerManager = (HandlerManager) getObject("handlerManager");
    final Handler fileHandlerV2 = new FileHandlerV2("default");
    handlerManager.create("default", fileHandlerV2);
    // 默认Recorder。
    RecorderManager recorderManager = (RecorderManager) getObject("recorderManager");
    final RecorderOption recorderOption = new LogRecorderOption();
    recorderManager.create(recorderOption);
  }

  @Override
  public final void addBean(final String name, final Bean bean) {
    this.beanFactory.addBean(name, bean);
  }

  @Override
  public final Bean getBean(final String name) {
    return this.beanFactory.getBean(name);
  }

  @Override
  public final Object getObject(final String name) {
    return this.beanFactory.getObject(name);
  }

  private void parser(final Class<?> classObj) throws Exception {
    // 这个类有Component注解吗?
    final Component component = classObj.getAnnotation(Component.class);
    if (null != component) {
      // 是单例吗?
      final boolean singleton = component.singleton();
      //
      String beanName = component.value();
      if (beanName.isEmpty()) {
        final String simpleName = classObj.getSimpleName();
        final String substring1 = simpleName.substring(0, 1);
        final String s = substring1.toLowerCase(Locale.getDefault());
        final String substring2 = simpleName.substring(1);
        beanName = s + substring2;
      }
      // 构造函数注入.
      final Executable[] constructors = classObj.getConstructors();
      for (final Executable constructor : constructors) {
        final ConstructorResource constructorResource =
            constructor.getAnnotation(ConstructorResource.class);
        Object object = null;
        if (null != constructorResource) {
          final Class<?>[] parameterTypes = constructor.getParameterTypes();
          final int parameterCount = constructor.getParameterCount();
          final Object[] objArr = new Object[parameterCount];
          final String[] names = constructorResource.value();
          for (int i = 0; i < parameterCount; i++) {
            // 查看有没有对应字段名的对象.
            final Bean bean = this.beanFactory.getBean(names[i]);
            if (null != bean) {
              //
              Object obj = bean.getObj();
              if (null == obj) {
                obj = bean.getClassObj().getConstructor().newInstance();
              }
              objArr[i] = obj;
            }
          }
          if (singleton) {
            object = classObj.getConstructor(parameterTypes).newInstance(objArr);
          }
        } else {
          // 没有构造函数有注解.
          if (singleton) {
            object = classObj.getConstructor().newInstance();
          }
        }
        final Bean bean = new BeanService();
        bean.setClassObj(classObj);
        bean.setName(beanName);
        if (singleton) {
          bean.setSingleton(true);
          bean.setObj(object);
        } else {
          bean.setSingleton(false);
          bean.setObj(null);
          // 不需要级联注入,当获取class类时,使用时动态注入.
        }
        this.beanFactory.addBean(beanName, bean);
      }
    }
  }
}
