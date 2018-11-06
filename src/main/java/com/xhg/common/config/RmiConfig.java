/*
package com.xhg.common.config;

import com.xhg.studyelement.serivce.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.rmi.RmiServiceExporter;

*/
/**
 * RMI服务
 *
 * @author 16033
 *//*

@Configuration
public class RmiConfig {

    @Bean
    public RmiServiceExporter rmiServiceExporter(UserService userService){
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setService(userService);
        rmiServiceExporter.setServiceName("userService");
        rmiServiceExporter.setServiceInterface(UserService.class);
        rmiServiceExporter.setRegistryHost("rmi.replies.com");
        rmiServiceExporter.setRegistryPort(1199);
        return rmiServiceExporter;
    }

    @Bean
    public RmiProxyFactoryBean userService(){
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1199/userService");
        rmiProxyFactoryBean.setServiceInterface(UserService.class);
        return rmiProxyFactoryBean;
    }

}
*/
