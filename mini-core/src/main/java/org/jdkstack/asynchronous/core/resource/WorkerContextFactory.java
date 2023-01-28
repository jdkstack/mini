package org.jdkstack.asynchronous.core.resource;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdkstack.asynchronous.api.context.WorkerContext;

public class WorkerContextFactory {

  private final Map<String, WorkerContext> threads = new ConcurrentHashMap<>(32);

}
