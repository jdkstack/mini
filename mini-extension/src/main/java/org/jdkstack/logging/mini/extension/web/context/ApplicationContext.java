package org.jdkstack.logging.mini.extension.web.context;

import org.jdkstack.logging.mini.extension.web.annotation.ComponentScan;
import org.jdkstack.logging.mini.extension.web.annotation.GetMapping;
import org.jdkstack.logging.mini.extension.web.annotation.PathVariable;
import org.jdkstack.logging.mini.extension.web.annotation.PostMapping;
import org.jdkstack.logging.mini.extension.web.annotation.RequestBody;
import org.jdkstack.logging.mini.extension.web.annotation.RequestMapping;
import org.jdkstack.logging.mini.extension.web.annotation.RequestParam;
import org.jdkstack.logging.mini.extension.web.annotation.RestController;
import org.jdkstack.logging.mini.extension.web.data.ControllerMetaData;
import org.jdkstack.logging.mini.extension.web.data.MethodMetaData;
import org.jdkstack.logging.mini.extension.web.data.ParameterMetaData;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationContext implements Context {

    /**
     * 存储RestController简单名和全限定名的映射,把类主动加载到就jvm中
     */
    public Map<String, List<ParameterMetaData>> fullyPathsParams = new HashMap<>();
    /**
     * 存储RestController简单名和全限定名的映射,把类主动加载到就jvm中
     */
    public Map<String, MethodMetaData> fullyPaths = new HashMap<>();
    /**
     * 存储RestController简单名和全限定名的映射,把类主动加载到就jvm中
     */
    public Map<String, ControllerMetaData> controllerMetaDataMap = new HashMap<>();
    /**
     * Http URL路径的分割符
     */
    public String pathSeparator = "/";

    /**
     * 存储RestController的根URL路径和全限定名的映射,把类主动加载到就jvm中
     */
    private Map<String, Class<?>> fullyQualifiedClasses = new HashMap<>();

    @Override
    public final void scan(Class<?> application) {
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
            //保存方法元数据
            for (Map.Entry<String, ControllerMetaData> entry : controllerMetaDataMap.entrySet()) {
                String basePath = entry.getKey();
                ControllerMetaData controllerMetaData = entry.getValue();
                Class<?> classObj = controllerMetaData.getClassObj();
                methodMetaData(classObj, basePath);
            }
            //保存参数元数据
            for (Map.Entry<String, MethodMetaData> entry : fullyPaths.entrySet()) {
                String fullPath = entry.getKey();
                MethodMetaData methodMetaData = entry.getValue();
                Method method = methodMetaData.getMethod();
                parameterMetaData(method, fullPath);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void parser(final Class<?> classObj) throws Exception {
        this.controllerMetaData(classObj);
    }

    /**
     * 控制器元数据 反射获取Class中信息 , String configBasePath
     *
     * @param clazz clazz
     */
    protected void controllerMetaData(Class<?> clazz) {
        try {
            //要想暴露Http Action接口,必须有RestController注解
            RestController restController = clazz.getAnnotation(RestController.class);
            if (restController == null) {
                //System.out.println("Controller {} 没有@RestController注解,忽略加载这个类.", clazz.getSimpleName());
                return;
            }
            RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
            //Http Action接口URL的跟路径
            String[] basePath = requestMapping != null ? requestMapping.value() : null;
            //如果注解的basePath不正确,则忽略给定的值,用""代替(必须/开头,后面必须至少有一个字符)
            if (!basePath[0].startsWith(pathSeparator) || basePath[0].length() == 1) {
                basePath = null;
            }
            this.fullyQualifiedClasses.put(basePath[0], clazz);
            //用来校验basePath是否正确
            //参考 插件配置文件中的name
            //if (!configBasePath.equals(basePath)) {
            //   throw new RuntimeException("fullyQualifiedNames中配置的basePath与Rest类中basePath不匹配.");
            //}
            //单例还是多,默认是单例
            boolean singleton = restController.singleton();
            //System.out.println("Rest Controller Singleton: {} ,basePath: {}", singleton, basePath);
            if (singleton) {
                //创建一个对象,反射方法时,对象作为参数传入
                Constructor<?> constructor = clazz.getConstructor();
                //使用构造方法,反射创建对象
                Object obj = constructor.newInstance();
                //一个Rest类只有一个basePath,对应一个对象
                controllerMetaDataMap.put(basePath[0], new ControllerMetaData(true, obj, clazz));
            } else {
                //如果是多例,不保存对象,使用的时候动态的创建对象
                controllerMetaDataMap.put(basePath[0], new ControllerMetaData(false, null, clazz));
            }
        } catch (Exception e) {
            //LOG.error("解析Class类出现异常,消息: {}.", e.getMessage());
        }
    }

    /**
     * 元数据方法 保存方法的元数据信息
     *
     * @param clazz    clazz
     * @param basePath 基本路径
     */
    protected void methodMetaData(Class<?> clazz, String basePath) {
        //循环所有方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            //Rest Controller 类每一个方法上,有且只能有一个注解
            Annotation[] annotations = method.getAnnotations();
            if (annotations.length != 1) {
                //System.out.println("REST Controller: {} 方法 {} 只能包含一个 Http Action 注解.", clazz.getSimpleName(), method.getName());
                continue;
            }
            //获取这个唯一注解的类型,分别进行判断,是不是HttpAction动作的注解类型
            Class<? extends Annotation> annotationClass = annotations[0].annotationType();
            String methodPath = getMethodPath(method, annotationClass);
            //如果不符合path或者数量不符合
            if (!methodPath.startsWith(pathSeparator) || methodPath.length() == 1) {
                throw new RuntimeException("xx");
            }
            //http rest 方法的请求全路径
            String fullPath = basePath + methodPath;
            fullyPaths.put(fullPath, new MethodMetaData(method, fullPath, basePath));
        }
    }

    private void customMethod(Parameter parameter, ParameterMetaData pmd) {
        //如果参数注解是RequestParam
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        if (requestParam != null) {
            pmd.setParameterName(requestParam.value());
        }
        //如果参数注解是RequestBody
        RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
        if (requestBody != null) {
            pmd.setParameterName(requestBody.value());
        }
        //如果参数注解是PathVariable
        PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
        if (pathVariable != null) {
            pmd.setParameterName(pathVariable.value());
        }
    }

    /**
     * 参数元数据(Executable 代替 Method) 保存参数的元数据信息
     *
     * @param method   方法
     * @param fullPath 完整路径
     */
    protected void parameterMetaData(Executable method, String fullPath) {
        Parameter[] parameters = method.getParameters();
        List<ParameterMetaData> params = new ArrayList<>();
        for (Parameter parameter : parameters) {
            ParameterMetaData pmd = new ParameterMetaData();
            //得到参数的类型
            Class<?> type = parameter.getType();
            //得到参数的名字(反射只能获取arg0,arg1等)
            String name = parameter.getName();
            //设置参数的类型
            pmd.setParameterType(type);
            Annotation[] annotations = parameter.getAnnotations();
            if (annotations.length == 0) {
                pmd.setAnnotation(false);
                //String simpleName = type.getSimpleName();
                if ("arg0".equals(name)) {
                    pmd.setParameterName("request");
                } else if ("arg1".equals(name)) {
                    pmd.setParameterName("response");
                } else {
                    //throw new RuntimeException("原始方法{},全路径,{}暂时不支持超过2个", method.getName(), fullPath);
                }
            }
            if (annotations.length != 0) {
                customMethod(parameter, pmd);
            }
            //添加参数对象
            params.add(pmd);
        }
        //全路径+参数
        fullyPathsParams.put(fullPath, params);
    }

    /**
     * get方法路径
     *
     * @param method          方法
     * @param annotationClass 注释类
     * @return {@link String}
     */
    public String getMethodPath(Method method, Class<? extends Annotation> annotationClass) {
        String[] methodPath = null;
        //如果Rest 方法注解是GetMapping
        if (annotationClass.equals(GetMapping.class)) {
            methodPath = method.getAnnotation(GetMapping.class).value();
        }
        if (annotationClass.equals(PostMapping.class)) {
            methodPath = method.getAnnotation(PostMapping.class).value();
        }
        return methodPath[0];
    }

    @Override
    public Map<String, List<ParameterMetaData>> getFullyPathsParams() {
        return this.fullyPathsParams;
    }

    @Override
    public void setFullyPathsParams(final Map<String, List<ParameterMetaData>> fullyPathsParams) {
        this.fullyPathsParams = fullyPathsParams;
    }

    @Override
    public Map<String, MethodMetaData> getFullyPaths() {
        return this.fullyPaths;
    }

    @Override
    public void setFullyPaths(final Map<String, MethodMetaData> fullyPaths) {
        this.fullyPaths = fullyPaths;
    }

    @Override
    public Map<String, ControllerMetaData> getControllerMetaDataMap() {
        return this.controllerMetaDataMap;
    }

    @Override
    public void setControllerMetaDataMap(final Map<String, ControllerMetaData> controllerMetaDataMap) {
        this.controllerMetaDataMap = controllerMetaDataMap;
    }

    @Override
    public Map<String, Class<?>> getFullyQualifiedClasses() {
        return this.fullyQualifiedClasses;
    }

    @Override
    public void setFullyQualifiedClasses(final Map<String, Class<?>> fullyQualifiedClasses) {
        this.fullyQualifiedClasses = fullyQualifiedClasses;
    }
}
