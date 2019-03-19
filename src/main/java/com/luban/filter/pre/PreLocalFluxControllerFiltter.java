package com.luban.filter.pre;

import com.luban.container.RequestContext;
import com.luban.filter.LuAroundFilter;
import com.luban.model.KeyBucket;
import com.luban.model.exception.LuException;
import com.luban.model.exception.LuExceptionEnum;
import com.luban.utils.IpUtils;

/**
 * 本地流通量控制，启动令牌桶
 * 令牌桶规则：
 * 每秒刷新令牌，添加令牌个数到令牌桶当中，如果令牌桶已经满，则不再添加
 *
 */
public class PreLocalFluxControllerFiltter extends LuAroundFilter {
    //实例化令牌桶
    protected static KeyBucket keyBucket = new KeyBucket();
    @Override
    public void before() throws LuException {
        System.out.println("执行前置方法");
        RequestContext ctx =RequestContext.getCurrentContext();
        String ip = IpUtils.getIpAddress(ctx.getRequest());
        boolean pix = PreLocalFluxControllerFiltter.keyBucket.ifAllowCall(ip);
        //如果无法访问则直接返回错误
        if(!pix){
            throw new LuException(LuExceptionEnum.SYSTEM_ERROR,"访问连接过多，访问出错");
        }

    }

    @Override
    public void after()throws LuException {
        System.out.println("执行后置方法");
        try {
            RequestContext ctx =RequestContext.getCurrentContext();
            String ip = IpUtils.getIpAddress(ctx.getRequest());
            PreLocalFluxControllerFiltter.keyBucket.recoveryKey(ip);
        }catch (RuntimeException e){
            throw new LuException(LuExceptionEnum.SYSTEM_ERROR,e.getMessage());
        }

    }
}
