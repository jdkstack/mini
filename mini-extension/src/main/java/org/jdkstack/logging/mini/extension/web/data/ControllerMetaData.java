package org.jdkstack.logging.mini.extension.web.data;

/**
 * 控制器元数据
 *
 * @author admin
 */
public class ControllerMetaData {

    private boolean singleton;
    private Object obj;
    private Class<?> classObj;

    public ControllerMetaData(final boolean singleton, final Object obj, final Class<?> classObj) {
        this.singleton = singleton;
        this.obj = obj;
        this.classObj = classObj;
    }

    public Class<?> getClassObj() {
        return this.classObj;
    }

    public void setClassObj(final Class<?> classObj) {
        this.classObj = classObj;
    }

    public Object getObj() {
        return this.obj;
    }

    public void setObj(final Object obj) {
        this.obj = obj;
    }

    public boolean isSingleton() {
        return this.singleton;
    }

    public void setSingleton(final boolean singleton) {
        this.singleton = singleton;
    }
}
