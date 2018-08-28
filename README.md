# 一个简单的Spring Boot 日志工具

## 第一种使用方式
    直接在方法上使用@CommonLog注解 将会输出该方法的入参出参 默认级别为INFO
    可以自行使用value注解配置日志级别 例如@CommLog(value="WARN")
## 第二种使用方式
    直接输出*.service.impl包下的所有*Impl类下所有public方法的入参出参 默认不开启 
    需要配置core.log.auto=true进行开启 不配置不开启
    如果在*Impl类的public方法上使用@CommonLog，将优先使用注解的日志级别，日志不会重复输出
## 日志类型
    DEBUG INFO WARN ERROR TRACE 不用区分大小写
## 目前问题
    如果在主类Appcation上使用日志输出 类名将会带有代理的标志
## 中心库引用
    <dependency>
           <groupId>com.xfmeet</groupId>
           <artifactId>aspect-log</artifactId>
           <version>1.0.0</version>
    </dependency>
    
 需要引入
 
    <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>
    
*如果fastjson冲突请排除掉aspect-log中的fastjson引用*
    
