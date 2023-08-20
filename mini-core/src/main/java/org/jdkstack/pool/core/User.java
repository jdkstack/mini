package org.jdkstack.pool.core;

public class User implements  Runnable{

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public void run() {
    //System.out.println("name:"+name);
    }
}
