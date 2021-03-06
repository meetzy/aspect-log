package com.xfmeet.core.log;


import com.xfmeet.core.log.aspect.ServiceLogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author meetzy
 */
@ComponentScan
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LogConfig {

    @Bean
    @ConditionalOnProperty(prefix = "core.log", name = "auto", havingValue = "true")
    public ServiceLogAspect controllerLogAspect() {
        return new ServiceLogAspect();
    }
}
