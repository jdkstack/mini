package org.jdkstack.logging.mini.extension.web.data;

/**
 * 参数元数据
 *
 * @author admin
 */
public class ParameterMetaData {

    private String parameterName;
    private Class<?> parameterType;
    private boolean isAnnotation = true;

    public ParameterMetaData() {
    }

    public ParameterMetaData(final String parameterName, final Class<?> parameterType) {
        this.parameterName = parameterName;
        this.parameterType = parameterType;
    }

    public boolean isAnnotation() {
        return this.isAnnotation;
    }

    public void setAnnotation(final boolean annotation) {
        this.isAnnotation = annotation;
    }

    public String getParameterName() {
        return this.parameterName;
    }

    public void setParameterName(final String parameterName) {
        this.parameterName = parameterName;
    }

    public Class<?> getParameterType() {
        return this.parameterType;
    }

    public void setParameterType(final Class<?> parameterType) {
        this.parameterType = parameterType;
    }
}
