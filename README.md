# Scaffold

##介绍
* Scaffold是一个JavaEE企业级项目的快速开发的脚手架，提供了底层抽象和通用功能，快速搭建，随配随用。
* maven结构分为common,config,web三块
* 本项目为个人简单搭建，后续会断续更新，

### 核心功能
* 通用的DAO、Service、Controller抽象，从CRUD中解放
* 简单的分页、排序、查询抽象，更快的开发速度(待实现)
* 借助spring实现简单的数据绑定、类型转换、验证、格式化(待实现)
* 提供基于资源的细粒度授权管理，灵活的授权模型(待实现)
* 提供常用功能的用例，直接拿来用即可，比如文件上传、大数据量Excel导入导出等等
* 动态增删任务调度，调度任务监控，配置，控制运行，
* 通用的文件上传/下载并进行资源管理(待实现)
* .....

### 技术选型

#### 管理
* maven依赖和项目管理
* git版本控制

#### 后端
* IoC容器 spring , springmvc , cglib动态代理AOP实现
* orm框架 mybatis (待实现) , 验证框架 hibernate validator
* 安全框架 shiro(待实现)
* 任务调度框架 quartz
* 消息队列 rabbitmq + activemq (实际使用可选其中合适之一)
* 缓存 Memcached + Redis缓存/集群
* 数据源 druid
* 日志 log4j
* jsp 模板视图 velocity(待实现)

#### 前端
* jquery js框架
* Bootstrap - gentelella 管理界面模版
* font-wesome 字体/图标框架
* jquery Validation Engine 验证框架（配合spring的验证框架）
* dataTable表格控件
* bootstrap-fileinput文件上传插件
* Echarts / highcharts图表插件
* jquery blockUI/confirm.js 弹出框/遮罩框架
* jquery-fileupload 文件上传
* bootstrap-datatimepicker 日历选择
* ......


#### 数据库
 * 目前只支持mysql，建议mysql5.5及以上


###支持的浏览器
 * chrome
 * 其他浏览器暂时未测试

