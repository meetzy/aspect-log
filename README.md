# 一个简单的Spring Boot 日志工具

## 第一种使用方式
    直接使用@CommonLog注解，将会输出方法的入参出参，默认级别为INFO，
    可以自行使用value注解配置日志级别，例如@CommLog(value="WARN")
## 第二种方式
    直接输出serviceImpl的方法的入参出参，默认不开启，需要配置core.log.auto=true
    进行开启。
## 日志类型
    DEBUG INFO WARN ERROR TRACE 不用区分大小写
