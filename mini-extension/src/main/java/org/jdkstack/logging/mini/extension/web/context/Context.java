package org.jdkstack.logging.mini.extension.web.context;

import java.util.List;
import java.util.Map;
import org.jdkstack.logging.mini.extension.web.data.ControllerMetaData;
import org.jdkstack.logging.mini.extension.web.data.MethodMetaData;
import org.jdkstack.logging.mini.extension.web.data.ParameterMetaData;

public interface Context {

  void scan(Class<?> application);

  Map<String, List<ParameterMetaData>> getFullyPathsParams();

  void setFullyPathsParams(Map<String, List<ParameterMetaData>> fullyPathsParams);

  Map<String, MethodMetaData> getFullyPaths();

  void setFullyPaths(Map<String, MethodMetaData> fullyPaths);

  Map<String, ControllerMetaData> getControllerMetaDataMap();

  void setControllerMetaDataMap(Map<String, ControllerMetaData> controllerMetaDataMap);

  Map<String, Class<?>> getFullyQualifiedClasses();

  void setFullyQualifiedClasses(Map<String, Class<?>> fullyQualifiedClasses);
}
