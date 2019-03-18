package com.luban.filter.pre;

import com.luban.annotation.EnableLuFilter;
import com.luban.http.HttpRoute;

@EnableLuFilter
public class MyPreForwardFilter extends PreForwardFilter {
    public MyPreForwardFilter(){
        preRule = 1;
        createHttpRouteList();
    }

    @Override
    public void createHttpRouteList() {
        httpRouteList.add(new HttpRoute("http://localhost:20001","/api"));
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
