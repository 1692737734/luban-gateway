package com.luban.model;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 令牌桶
 */
public class KeyBucket {
    private int keyByTime = 10;   //每秒生成令牌数量
    private int keyBucket = 100;  //令牌桶的总容量

    private int key;                //当前剩余令牌

    private int useKey;             //使用中的key

    private Map<String,Integer> ipCallFrequencyMap = new HashMap<String, Integer>();     //ip使用令牌记录

    private int ipLimit;            //ip限制，如果为0，则不做任何限制

    public int getKeyByTime() {
        return keyByTime;
    }

    public void setKeyByTime(int keyByTime) {
        this.keyByTime = keyByTime;
    }

    public int getKeyBucket() {
        return keyBucket;
    }

    public void setKeyBucket(int keyBucket) {
        this.keyBucket = keyBucket;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getUseKey() {
        return useKey;
    }

    public void setUseKey(int useKey) {
        this.useKey = useKey;
    }


    public int getIpLimit() {
        return ipLimit;
    }

    public void setIpLimit(int ipLimit) {
        this.ipLimit = ipLimit;
    }
    //是否允许调用
    public boolean ifAllowCall(String ip){
        if(StringUtils.isEmpty(ip)){
            return false;
        }
        if(ipLimit == 0){
            return true;
        }
        if(ipLimit!=0){
            return getAllowKey(ip);
        }else {
            return getAllowKey();
        }
    }

    /**
     * 恢复令牌
     * @param ip
     */
    public void recoveryKey(String ip){
        if(!StringUtils.isEmpty(ip)){
           synchronized (this){
               key = key +1;
               useKey = useKey -1;
           }
           if(ipLimit>0){
               synchronized (this){
                   int ipCount = ipCallFrequencyMap.get(ip);
                   if(ipCount > 0){
                       ipCount = ipCount -1;
                       ipCallFrequencyMap.put(ip,ipCount);
                   }
               }
           }
        }
    }
    //获取令牌
    private boolean getAllowKey(){
        synchronized (this){
            if(key>0){
                key = key -1;
                useKey = useKey + 1;
                return true;
            }else {
                return false;
            }
        }
    }
    //ip限制情况下获取令牌
    private boolean getAllowKey(String ip){
        synchronized (this){
            if(key>0){
                //根据ip获取令牌使用情况
                Integer ipCount = ipCallFrequencyMap.get(ip);
                if(ipCount == null){
                    ipCount = 0;
                }
                if(ipCount>ipLimit){
                    return false;
                }
                key = key -1;
                useKey = useKey + 1;
                ipCount = ipCount +1;
                ipCallFrequencyMap.put(ip,ipCount);
                return true;
            }else {
                return false;
            }
        }
    }


}
