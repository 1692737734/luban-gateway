package com.luban.filter.pre.my;

import com.luban.annotation.EnableLuFilter;
import com.luban.filter.pre.PreForwardFilter;
import com.luban.http.HttpRoute;

@EnableLuFilter
public class MyPreForwardFilter extends PreForwardFilter {
    public MyPreForwardFilter(){
        preRule = 1;
        createHttpRouteList();
    }

    @Override
    public void createHttpRouteList() {
        httpRouteMap.put("/api","http://localhost:20001");
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
