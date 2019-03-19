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
  
###添加过滤器
添加过滤器分为三类过滤器。分别为：**环绕过滤器**、**前置过滤器**、**后置过滤器**

下面就以两种过滤器为例子说明如何使用

####路由过滤器
路由过滤器主要是为了提供路由匹配，其包含两个设置项

preRule:匹配规则，就是将url中的前几个作为匹配关键字进行匹配

httpRouteMap：路由匹配保存map,该map的key为匹配关键字，value为匹配域名
  
  以下就是创建路由过滤器的例子
  
    ```
    
    //将当期类作为过滤器
    
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