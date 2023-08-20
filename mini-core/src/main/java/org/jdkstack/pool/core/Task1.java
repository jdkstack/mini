package org.jdkstack.pool.core;

import org.jdkstack.ringbuffer.core.EventFactory;

public class Task1<E> implements EventFactory<E> {
    
    @Override
    public E newInstance() {
    return  (E) new User();
    }
}
