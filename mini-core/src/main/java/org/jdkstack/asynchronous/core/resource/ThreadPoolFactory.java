package org.jdkstack.asynchronous.core.resource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import org.jdkstack.bean.core.annotation.Component;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
@Component
public class ThreadPoolFactory {

  /** . */
  private final Map<String, ExecutorService> executorServices = new ConcurrentHashMap<>(10);

}
