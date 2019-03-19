package com.luban.config;

import com.luban.annotation.EnableLuFilter;
import com.luban.container.CallChainContainer;
import com.luban.filter.LuFilter;
import com.luban.http.LuServlet;
import com.luban.parameter.FilterType;
import com.luban.utils.SortUtils;
import com.luban.utils.SpringUtils;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 鲁班网关配置，承担所有配置任务
 */
@Configuration
public class LuProxyConfiguration implements Ordered {

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new LuServlet(),"/*");
    }

    /**
     * 通过spring容器获取所有的调用链
     */
    @Bean
    public String initializationCallChain() throws Exception {
        List<LuFilter> preFilter = new ArrayList<LuFilter>();
        List<LuFilter> routeFilter = new ArrayList<LuFilter>();
        List<LuFilter> postFilter = new ArrayList<LuFilter>();
        List<LuFilter> aroundFilter = new ArrayList<LuFilter>();

        Map<String, Object> beanWithAnnotationMap = SpringUtils.getApplicationContext().getBeansWithAnnotation(EnableLuFilter.class);

        Class<? extends Object> clazz = null;
        for(Map.Entry<String,Object> entry:beanWithAnnotationMap.entrySet()){
            clazz = entry.getValue().getClass();
            Object obj = entry.getValue();
            if(obj instanceof LuFilter){
                LuFilter luFilter = (LuFilter) obj;
                String type = luFilter.filterType();
                System.out.println(clazz.getName()+":"+type);
                if(type.equals(FilterType.PRE)){
                    //预处理
                    preFilter.add(luFilter);
                }
                if(type.equals(FilterType.POST)){
                    postFilter.add(luFilter);
                }
                if(type.equals(FilterType.ROUTE)){
                    routeFilter.add(luFilter);
                    //假设route数量大于1
                    if(routeFilter.size()>1){
                        throw new Exception();
                    }
                }
                if(type.equals(FilterType.AROUND)){
                    aroundFilter.add(luFilter);
                }
            }else {
                continue;
            }
        }
        //对获取的数据进行排序
        preFilter = SortUtils.sortLuFilter(preFilter);
        postFilter = SortUtils.sortLuFilter(postFilter);
        aroundFilter = SortUtils.sortLuFilter(aroundFilter);

        CallChainContainer.hashFilters.put(FilterType.PRE,preFilter);
        CallChainContainer.hashFilters.put(FilterType.POST,postFilter);
        CallChainContainer.hashFilters.put(FilterType.ROUTE,routeFilter);
        CallChainContainer.hashFilters.put(FilterType.AROUND,aroundFilter);

        return "";
    }


    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE+2;
    }


}
