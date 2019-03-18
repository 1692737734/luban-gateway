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
  
  4 添加过滤器
  
  添加pre过滤器（添加路由过滤器）
  
    ```
    //将当期类作为过滤器
    @EnableLuFilter  
    public class MyPreForwardFilter extends PreForwardFilter {
        public MyPreForwardFilter(){
            preRule = 1;
            createHttpRouteList();
        }
        //添加路由配置
        @Override
        public void createHttpRouteList() {
            httpRouteList.add(new HttpRoute("http://localhost:20001","/api"));
        }
        //设置优先级
        @Override
        public int filterOrder() {
            return 0;
        }
    }
    ```

