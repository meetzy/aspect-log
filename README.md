# 一个简单的Spring Boot 日志工具

## 第一种使用方式
    直接使用@CommonLog注解，将会输出方法的入参出参，默认级别为INFO，
    可以自行使用value注解配置日志级别，例如@CommLog(value="WARN")
## 第二种使用方式
    直接输出*.service.impl包下的所有*Impl类下所有public方法的入参出参，
    默认不开启，需要配置core.log.auto=true进行开启。
    如果在Impl类的public方法上使用@CommonLog，将优先使用注解的日志级别，日志不会重复输出
## 日志类型
    DEBUG INFO WARN ERROR TRACE 不用区分大小写
    
## 目前问题
    如果在主类上使用日志输出，类名将会带有代理的标志，还没想好是否要处理掉
---
# One simple spring boot log tool by aspect

## The first way to use it 
    Use@CommonLog output method args and return, default level is info,
    you can @CommonLog("level") custom log level.
## The second way to use it
    Direct output *.service.impl path all *Impl class public method args and return,
    default not enbale,you can set core.log.auto=ture to enable. 
## Log type
    DEBUG INFO WARN ERROR TRACE  is case insensitive.

