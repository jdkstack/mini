package org.jdkstack.logging.mini.extension.plugin.rest;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.jdkstack.logging.mini.extension.plugin.Plugin;
import org.jdkstack.logging.mini.extension.plugin.PluginEntity;
import org.jdkstack.logging.mini.extension.plugin.PluginServiceLifecycle;
import org.jdkstack.logging.mini.extension.plugin.PluginServices;
import org.jdkstack.logging.mini.extension.web.annotation.GetMapping;
import org.jdkstack.logging.mini.extension.web.annotation.PostMapping;
import org.jdkstack.logging.mini.extension.web.annotation.RestController;
import org.jdkstack.logging.mini.extension.web.data.ControllerMetaData;
import org.jdkstack.logging.mini.extension.web.data.MethodMetaData;

/**
 * rest服务模块的生命周期
 *
 * @author admin
 */
public final class RestServiceModuleLifecycle extends AbstractRestServiceModuleLifecycle {

  private static RestServiceModuleLifecycle instance = new RestServiceModuleLifecycle();
  private Map<String, String> fullyQualifiedNames = new HashMap<>();

  /**
   * 存储RestController的根URL路径和全限定名的映射,把类主动加载到就jvm中
   */
  private Map<String, Class<?>> fullyQualifiedClasses = new HashMap<>();

  private RestServiceModuleLifecycle() {
  }

  public static RestServiceModuleLifecycle getInstance() {
    return instance;
  }

  public void doInit() throws Exception {
    // 手动配置Rest类静态信息
    // 每增加一个Rest类,都需要在这个配置
    fullyQualifiedNames.put("user", "org.nation.core.server.core.http.rest.controller.UserController");
    fullyQualifiedNames.put("base", "org.nation.core.server.core.http.rest.controller.TestController");
    // 加载Rest类到jvm,保存类元数据和方法元数据
    for (Map.Entry<String, String> entry : fullyQualifiedNames.entrySet()) {
      // Rest类简单名称(类名)
      String basePath = entry.getKey();
      // Rest类全限定名(包名+类型)
      String fullyQualifiedName = entry.getValue();
      // LOG.info("开始加载Rest Controller {} 全限定名 {}", basePath, fullyQualifiedName);
      // 用Class.forName方法返回类的Class
      Class<?> classObj = classForName(fullyQualifiedName);
      // 如果为空,jvm中不存在这个类
      if (classObj != null) {
        fullyQualifiedClasses.put(basePath, classObj);
      }
    }
    // 注册插件中的rest服务
    PluginServices pluginServices = PluginServices.getInstance();
    Map<String, PluginServiceLifecycle> pluginServiceLifecycles = pluginServices.getPluginServiceLifecycles();
    PluginServiceLifecycle pluginServiceLifecycle = pluginServiceLifecycles.get("rest");
    Map<String, PluginEntity> pluginEntitys = pluginServiceLifecycle.getPluginEntitys();
    // 注册插件中的rest服务
    for (Map.Entry<String, PluginEntity> entry : pluginEntitys.entrySet()) {
      String basePath = entry.getKey();
      PluginEntity pluginEntity = entry.getValue();
      // LOG.info("开始加载Rest Controller {} 全限定名 {}", basePath,
      // pluginEntity.getPluginMeta().getClassName());
      Class<? extends Plugin> classObj = pluginEntity.getPlugin().getClass();
      fullyQualifiedClasses.put(basePath, classObj);
    }
  }

  public void doStart() {
    for (Map.Entry<String, Class<?>> entry : fullyQualifiedClasses.entrySet()) {
      // 如果存在,利用反射,获取类的方法,注解,参数等数据,用Map保存起来
      // Netty Http服务拦截到客户端发起的Http请求以后,根据Http URL匹配Map中保存的数据
      // 包括方法,类,参数名,参数类型等
      // 配置的basePath 和插件中的name,暂时没有使用，但是不代表以后不用，内部使用
      // Rest controller 注解 Request Mapping的值代替了。
      // String basePath = entry.getKey();
      Class<?> classObj = entry.getValue();
      // , basePath
      controllerMetaData(classObj);
    }
    // 保存方法元数据
    for (Map.Entry<String, ControllerMetaData> entry : controllerMetaDataMap.entrySet()) {
      String basePath = entry.getKey();
      ControllerMetaData controllerMetaData = entry.getValue();
      Class<?> classObj = controllerMetaData.getClassObj();
      methodMetaData(classObj, basePath);
    }
    // 保存参数元数据
    for (Map.Entry<String, MethodMetaData> entry : fullyPaths.entrySet()) {
      String fullPath = entry.getKey();
      MethodMetaData methodMetaData = entry.getValue();
      Method method = methodMetaData.getMethod();
      parameterMetaData(method, fullPath);
    }
  }

  /**
   * get方法路径
   *
   * @param method          方法
   * @param annotationClass 注释类
   * @return {@link String}
   */
  public String getMethodPath(Method method, Class<? extends Annotation> annotationClass) {
    String methodPath = "";
    // 如果Rest 方法注解是GetMapping
    if (annotationClass.equals(GetMapping.class)) {
      methodPath = method.getAnnotation(GetMapping.class).value()[0];
    }
    if (annotationClass.equals(PostMapping.class)) {
      methodPath = method.getAnnotation(PostMapping.class).value()[0];
    }
    return methodPath;
  }

  /**
   * 检查元数据(AnnotatedElement 代替 class类) 用于进行检查操作
   *
   * @param classObj 类obj
   * @return boolean
   */
  public boolean checkMetaData(AnnotatedElement classObj) {
    RestController restController = classObj.getAnnotation(RestController.class);
    if (restController == null) {
      // LOG.error("Controller没有@RestController注解,忽略加载这个类.");
      return false;
    }
    // 如果是true,则正常单例加载类,如果是false,停止加载
    return restController.singleton();
  }

  /**
   * 全限定名获取Class类,用来反射获取类信息
   */
  public Class<?> classForName(String fullyQualifiedName) {
    Class<?> classObj = null;
    try {
      classObj = Class.forName(fullyQualifiedName);
    } catch (ClassNotFoundException e) {
      //
    }
    return classObj;
  }

  public Map<String, String> getFullyQualifiedNames() {
    return fullyQualifiedNames;
  }

  public Map<String, Class<?>> getFullyQualifiedClasses() {
    return fullyQualifiedClasses;
  }
}
