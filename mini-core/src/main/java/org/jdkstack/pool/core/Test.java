package org.jdkstack.pool.core;

import java.util.concurrent.TimeUnit;
import org.jdkstack.ringbuffer.core.mpmc.version3.MpmcBlockingQueueV3;

public class Test {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(4, 4, 0, TimeUnit.SECONDS, new MpmcBlockingQueueV3<>(4, new Task1<>()));
        
for(;;){
    User user8 = (User) threadPoolExecutor.getTaskWorker();
    user8.setName("x");
    threadPoolExecutor.start();
}        
        
 /*       try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }
}
