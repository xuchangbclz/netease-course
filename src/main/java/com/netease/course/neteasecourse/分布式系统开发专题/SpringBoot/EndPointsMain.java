package com.netease.course.neteasecourse.分布式系统开发专题.SpringBoot;

/**
 * Spring Boot2.x中，默认只开放了info、health两个端点，剩余的需要自己通过配置management.endpoints.web.exposure.include=
 *                                                                     management.endpoint.端点.enabled= 属性可以启用或禁用端点
 * /actuator/auditevents	显示当前应用程序的审计事件信息
 * /actuator/beans	        显示应用Spring Beans的完整列表
 * /actuator/caches	        显示可用缓存信息
 * /actuator/conditions	    显示自动装配类的状态及及应用信息
 * /actuator/configprops	显示所有 @ConfigurationProperties 列表
 * /actuator/env	        显示 ConfigurableEnvironment 中的属性
 * /actuator/flyway	        显示 Flyway 数据库迁移信息
 * /actuator/health	        显示应用的健康信息（未认证只显示status，认证显示全部信息详情）
 * /actuator/info	        显示任意的应用信息（在配置文件写info.xxx即可）
 * /actuator/liquibase	    展示Liquibase 数据库迁移
 * /actuator/metrics	    展示当前应用的 metrics 信息
 * /actuator/mappings	    显示所有 @RequestMapping 路径集列表
 * /actuator/scheduledtasks	显示应用程序中的计划任务
 * /actuator/sessions	    允许从Spring会话支持的会话存储中检索和删除用户会话。
 * /actuator/shutdown	    允许应用以优雅的方式关闭（默认情况下不启用）
 * /actuator/threaddump	    执行一个线程dump
 * /actuator/httptrace	    显示HTTP跟踪信息（默认显示最后100个HTTP请求 - 响应交换）
 *
 * @Author daituo
 * @Date
 **/
public class EndPointsMain {
}
