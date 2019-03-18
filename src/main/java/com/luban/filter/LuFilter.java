package com.luban.filter;

public abstract class LuFilter {
    abstract public String filterType();
    abstract public int filterOrder();
    abstract public void run();
}
