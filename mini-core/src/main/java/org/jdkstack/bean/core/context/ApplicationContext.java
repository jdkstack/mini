package org.jdkstack.bean.core.context;

import java.lang.reflect.Executable;
import java.util.Locale;
import org.jdkstack.bean.core.annotation.Component;
import org.jdkstack.bean.core.annotation.ComponentScan;
import org.jdkstack.bean.core.annotation.ConstructorResource;
import org.jdkstack.bean.core.bean.BeanService;
import org.jdkstack.bean.core.factory.BeanFactory;
import org.jdkstack.bean.api.bean.Bean;
import org.jdkstack.bean.api.context.Context;
import org.jdkstack.bean.api.factory.Factory;

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

  private void parser(final Class<?> classObj) throws Exception {
    // 这个类有Component注解吗?
    final Component component = classObj.getAnnotation(Component.class);
    if (null != component) {
      // 是单例吗?
      final boolean singleton = component.singleton();
      //
      String beanName = component.value();
      if ("".equals(beanName)) {
        final String simpleName = classObj.getSimpleName();
        final String substring1 = simpleName.substring(0, 1);
        final String s = substring1.toLowerCase(Locale.getDefault());
        final String substring2 = simpleName.substring(1);
        beanName = s + substring2;
      }
      // 构造函数注入.
      final Executable[] constructors = classObj.getConstructors();
      for (final Executable constructor : constructors) {
        final ConstructorResource constructorResource = constructor.getAnnotation(ConstructorResource.class);
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
}
