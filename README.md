# luban-gateway
鲁班网关

## 使用教程
1 将项目拷贝到本地用maven进行打包

2 用编辑器引入相应的jar包

3 开启网关
     
     @SpringBootApplication
     @EnableLuProxy
     public class Application {
         public static void main(String[] args){
             SpringApplication.run(Application.class,args);
         }
     }
     
  添加@EnableLuProxy就开启了网关
  
### 添加过滤器
添加过滤器分为三类过滤器。分别为：**环绕过滤器**、**前置过滤器**、**后置过滤器**

下面就以两种过滤器为例子说明如何使用

#### 路由过滤器
路由过滤器主要是为了提供路由匹配，其包含两个设置项

preRule:匹配规则，就是将url中的前几个作为匹配关键字进行匹配

httpRouteMap：路由匹配保存map,该map的key为匹配关键字，value为匹配域名
  
  以下就是创建路由过滤器的例子
```
 @EnableLuFilter
    public class MyPreForwardFilter extends PreForwardFilter {
        public MyPreForwardFilter(){
            preRule = 1;
            createHttpRouteList();
        }
    
        @Override
        public void createHttpRouteList() {
            httpRouteMap.put("/api","http://localhost:20001");
        }
    
        @Override
        public int filterOrder() {
            return 0;
        }
    }    
```

例子说明：

@EnableLuFilter：该注解为将当期过滤器作为网关过滤器，不添加该注解当期类将不被作为过滤器使用

PreForwardFilter：继承了该类，PreForwardFilter为已经实现好的路由匹配方法，继承了该类，只要设置
路由规则以及路由规则map就可以达到路由匹配的效果

    
#### 流量控制过滤器

流量控制过滤器需要设置一个参数，就是设置

PreLocalFluxControllerFiltter.keyBucket.setKeyBucket(2);

这个方法就是设置需要设置当前能同时接受多少个请求，当大于这个数量，系统将不做任何相应

相关的代码为：

```
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
```


#### 自定义过滤器

自定义过滤器文档后续完善



