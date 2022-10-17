# 拍卖系统
## 前言
  拍卖项目是一个完整的拍卖系统，采用最新的流行的技术
## 项目功能介绍
   1. 给管理员使用的功能：
      - 审核拍品的发布----审核通过，和拒绝 
      - 发布拍品
      - 基本功能：商品类型的增删改查，拍卖状态的查询，管理员的增删改查，会员的。。。，成交状态的。。。
   2. 给会员使用的功能
      - 个人信息模块---登录，注册，修改密码，查看个人信息
      - 资金管理模块---充值，提现，按时间或者类型查询资金记录
      - 拍卖信息模块（核心模块）---发布拍品，参与竞拍， 拍卖信息查询，我的订单等

### 技术选型
  #### 后端技术
| 技术      |     说明     |     官网      |
|---------|:----------:|:-------------:|
| Spring boot | 容器 + MVC框架 |        https://spring.io/        |
| Mybatis-plus |   ORM框架    |  https://mybatis.org/mybatis-3/  |
| Mybatis-Generator |  数据层代码生成   |  https://mybatis.org/mybatis-3/  |
| quartz  |    定时任务    | http://www.quartz-scheduler.org/ |
| WebSocket |    实时推送    |                                  |
| Redis   |    ...     |                                  |
| JWT     |    登录支持    |                                  |
| Lombok  |     简化对象封装工具    |                                  |


####  前端技术
| 技术         |     说明     |     官网      |
|------------|:----------:|:-------------:|
| Vue2       |    前端框架    |       https://cn.vuejs.org/      |
| Vue-router |    路由框架    | https://cn.vuejs.org/  |
| Vuex       |  全局状态管理框架  |  	https://vuex.vuejs.org/  |
| Element    |   前端UI框架   | https://element.eleme.io |
| Axios      | 前端Http网络请求 |                                  |
| js-cookie  | cookie管理工具 |                                  |
| JWT        |    登录支持    |                                  |

