package com.luban.filter.pre;

import com.luban.container.RequestContext;
import com.luban.filter.LuFilter;
import com.luban.parameter.FilterType;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

//路由控制，为子类提供路由控制方法
//@EnableLuFilter
public class PreForwardFilter extends LuFilter {
    protected int preRule;      //截取规则
//    protected List<HttpRoute> httpRouteList = new ArrayList<HttpRoute>();
    //路由规则集合,使用map代替list优化查询速度
    protected Map<String,String> httpRouteMap = new HashMap<String, String>();

    public PreForwardFilter(){
        preRule = 1;
        createHttpRouteList();
    }

    public void createHttpRouteList(){ }

    /**
     * 定义过滤器类型，定义为final不可修改
     * @return
     */
    @Override
    public final String filterType() {
        return FilterType.PRE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }


    /**
     * 执行方法不可修改
     */
    @Override
    public final void run() {
        RequestContext ctx =RequestContext.getCurrentContext();
        HttpServletRequest servletRequest = ctx.getRequest();
        String targetURL = createTargetURL(servletRequest.getRequestURI());
        RequestEntity<byte[]> requestEntity = null;
        try {
            requestEntity = createRequestEntity(servletRequest, targetURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //4、将requestEntity放入全局threadlocal之中
        ctx.setRequestEntity(requestEntity);
    }

    /**
     * 构建最终路径
     * @param url
     * @return
     */
    private String createTargetURL(String url){
        if(preRule<0){
            return null;
        }
        //如果路由规则为0，则去map中查找key为/的，如果查找成功，则返回，不成功则返回null
        if(preRule == 0){
            String path = httpRouteMap.get("/");
            if(path == null){
                return null;
            }else {
                return path + url;
            }
        }
        String newUrl = "";
        String postUrl = "";
        String[] paths = url.split("/");
        for(int i = 1;i<preRule+1;i++){
            newUrl = newUrl + "/"+paths[i];
        }

        for(int i = preRule+1;i<paths.length;i++){
            postUrl = postUrl + "/"+paths[i];
        }
        String domain = httpRouteMap.get(newUrl);
        if(StringUtils.isEmpty(domain)){
            return null;
        }

        return domain + postUrl;

    }

    private RequestEntity createRequestEntity(HttpServletRequest request,String url) throws URISyntaxException, IOException {
        String method = request.getMethod();
        HttpMethod httpMethod = HttpMethod.resolve(method);
        //1、封装请求头
        MultiValueMap<String, String> headers =createRequestHeaders(request);
        //2、封装请求体
        byte[] body = createRequestBody(request);
        //3、构造出RestTemplate能识别的RequestEntity
        RequestEntity requestEntity = new RequestEntity<byte[]>(body,headers,httpMethod, new URI(url));
        return requestEntity;
    }

    private MultiValueMap<String, String> createRequestHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        List<String> headerNames = Collections.list(request.getHeaderNames());
        for(String headerName:headerNames) {
            List<String> headerValues = Collections.list(request.getHeaders(headerName));
            for(String headerValue:headerValues) {
                headers.add(headerName, headerValue);
            }
        }
        return headers;
    }

    private byte[] createRequestBody(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        return StreamUtils.copyToByteArray(inputStream);
    }
}
