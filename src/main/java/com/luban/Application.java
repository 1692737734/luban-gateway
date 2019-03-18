package com.luban;

import com.luban.annotation.EnableLuProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * spring启动，当前启动类用作测试使用
 */
@SpringBootApplication
@EnableLuProxy
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
