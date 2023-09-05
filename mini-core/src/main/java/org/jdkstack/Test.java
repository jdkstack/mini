package org.jdkstack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test {
  private static final Logger logger = LogManager.getLogger(Test.class);

  public static void main(String[] args) {
    long s = System.currentTimeMillis();
    for (int i = 0; i < 1000000000; i++) {
      abc();
      //final StackTraceElement[] elements = new Throwable().getStackTrace();
    }
    long e = System.currentTimeMillis();

    System.out.println(e - s);
  }

  public static void abc() {
     logger.atInfo().withLocation().log("Login for user {} failed");
    logger.atInfo().withLocation().log("Login for user {} failed");
  }
}
