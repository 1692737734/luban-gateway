package com.luban.filter.route;

import com.luban.annotation.EnableLuFilter;
import com.luban.container.RequestContext;
import com.luban.filter.LuFilter;
import com.luban.parameter.FilterType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
@EnableLuFilter
public class RoutingFilter extends LuFilter {
    @Override
    public String filterType() {
        return FilterType.ROUTE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public void run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        RequestEntity requestEntity = ctx.getRequestEntity();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = restTemplate.exchange(requestEntity,byte[].class);
        ctx.setResponseEntity(responseEntity);
    }
}
