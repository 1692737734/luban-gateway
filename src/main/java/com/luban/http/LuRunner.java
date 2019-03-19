package com.luban.http;

import com.luban.container.CallChainContainer;
import com.luban.container.RequestContext;
import com.luban.filter.LuAroundFilter;
import com.luban.filter.LuFilter;
import com.luban.parameter.FilterType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 调用链执行类
 */
public class LuRunner {
    public void init(HttpServletRequest request, HttpServletResponse response){
        RequestContext context = RequestContext.getCurrentContext();
        context.setRequest(request);
        context.setResponse(response);
    }
    public void preRoute(){
        runFilter(FilterType.PRE);
    }

    public void route(){
        runFilter(FilterType.ROUTE);
    }

    public void postRoute(){
        runFilter(FilterType.POST);
    }

    public void beforeRoute(){
        runAroundFilter(1);
    }
    public void afterRoute(){
        runAroundFilter(2);
    }

    public void runFilter(String type){
        List<LuFilter> filters = CallChainContainer.hashFilters.get(type);
        if(filters!=null){
            for(LuFilter luFilter:filters){
                luFilter.run();
            }
        }
    }

    public void runAroundFilter(int type){
        List<LuFilter> filters = CallChainContainer.hashFilters.get(FilterType.AROUND);
        if(filters!=null){
            for (LuFilter luFilter:filters){
                if(luFilter instanceof LuAroundFilter){
                    LuAroundFilter luAroundFilter = (LuAroundFilter) luFilter;
                    if(type == 1){
                        luAroundFilter.before();
                    }
                    if(type == 2){
                        luAroundFilter.after();
                    }

                }
            }
        }
    }
}
