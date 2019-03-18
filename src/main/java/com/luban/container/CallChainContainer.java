package com.luban.container;

import com.luban.filter.LuFilter;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 调用链对象
 */
public class CallChainContainer {
    public static ConcurrentHashMap<String, List<LuFilter>> hashFilters = new ConcurrentHashMap<String, List<LuFilter>>();
}
