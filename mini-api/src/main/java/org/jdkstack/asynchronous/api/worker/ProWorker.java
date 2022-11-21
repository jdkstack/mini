package org.jdkstack.asynchronous.api.worker;

import org.jdkstack.logging.mini.api.level.Level;

public interface ProWorker<E> extends Worker<E> {

  void setClassMethod(String classMethod);

  void setClassName(String className);

  void setLineNumber(int lineNumber);

  void setLogLevel(Level logLevel);

  void setMessage(StringBuilder message);
}
