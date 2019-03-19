package com.luban.filter;

import com.luban.parameter.FilterType;

/**
 * 环绕过滤器
 */
public class LuAroundFilter extends LuFilter{
    @Override
    public final String filterType() {
        return FilterType.AROUND;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public void run() {

    }

    public void before(){}

    public void after(){}


}
