package com.luban.utils;

import com.luban.filter.LuFilter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * 排序方法类
 */
public class SortUtils {
    /**
     * 对调用链进行排序
     * @param oldLuFilters
     * @return
     */
    public static List<LuFilter> sortLuFilter(List<LuFilter> oldLuFilters){
        //将list转换为数组进行排序
        LuFilter[] luFiltersArray = new LuFilter[oldLuFilters.size()];
        oldLuFilters.toArray(luFiltersArray);
        for(int i = 0;i<luFiltersArray.length-1;i++){
            for(int j=0;j<luFiltersArray.length-1-i;j++){
                if(luFiltersArray[j].filterOrder()<luFiltersArray[j+1].filterOrder()){
                    LuFilter temLuFilter = luFiltersArray[j];
                    luFiltersArray[j] = luFiltersArray[j+1];
                    luFiltersArray[j+1] = temLuFilter;
                }
            }
        }
        return Arrays.asList(luFiltersArray);
    }
}
