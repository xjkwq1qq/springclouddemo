## 启动环境
默认的profile为dev，其他环境通过指定启动参数使用不同的profile，比如：
```
测试环境：java -jar my-spring-boot.jar --spring.profiles.active=test
生产环境：java -jar my-spring-boot.jar --spring.profiles.active=prod
开发环境: java -jar my-spring-boot.jar --spring.profiles.active=dev
```

## 项目关系
### Spring cloud 环境现在包括以下部分
* eureka-server 服务发现   http://localhost:8671 查看状态
* cloud-admin-server 管理界面（可选）
* api-gateway api接口服务汇总（可选）
* ReportingServices  具体服务类（8001端口）

开发过程中只需要拉取eureka-server、ReportingServices即可运行
正式运行环境才需要运行cloud-admin-server、api-gateway

各个模块的代码地址：
```
eureka-server：http://10.173.40.70:3000/Data_Engine/eureka-server.git
cloud-admin-server: http://10.173.40.70:3000/Data_Engine/cloud-admin-server.git
api-gateway: http://10.173.40.70:3000/Data_Engine/api-gateway.git
ReportingServices: http://10.173.40.70:3000/Data_Engine/ReportingService.git
```

