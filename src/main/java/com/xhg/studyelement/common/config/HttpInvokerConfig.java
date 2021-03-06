package com.xhg.studyelement.common.config;

import com.xhg.studyelement.serivce.User1Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

/**
 * @author 16033
 */
//@Configuration
public class HttpInvokerConfig {

    @Bean
    public HttpInvokerServiceExporter burlapExporterUserService(User1Service userService){
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(userService);
        exporter.setServiceInterface(User1Service.class);
        return exporter;
    }

    /**
     * 访问HttpInvoker服务
     * @return
     */
    @Bean
    public HttpInvokerProxyFactoryBean userService(){
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:8080/");
        invoker.setServiceInterface(User1Service.class);
        return invoker;
    }

}
