package com.luban.filter.pre.my;

import com.luban.annotation.EnableLuFilter;
import com.luban.filter.pre.PreLocalFluxControllerFiltter;
@EnableLuFilter
public class MyPreLocalFluxControllerFilter extends PreLocalFluxControllerFiltter {
    public MyPreLocalFluxControllerFilter(){
        PreLocalFluxControllerFiltter.keyBucket.setKeyBucket(2);
    }
    @Override
    public int filterOrder() {
        return 0;
    }
}
