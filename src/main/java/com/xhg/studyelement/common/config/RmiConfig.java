package com.xhg.studyelement.common.config;

import com.xhg.studyelement.serivce.User1Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * RMI服务
 *
 * @author 16033
 */
@Configuration
public class RmiConfig {

    @Bean
    public RmiServiceExporter rmiServiceExporter(User1Service user1Service){
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setService(user1Service);
        rmiServiceExporter.setServiceName("user1Service");
        rmiServiceExporter.setServiceInterface(User1Service.class);
//        rmiServiceExporter.setRegistryHost("rmi.user.com");
        rmiServiceExporter.setRegistryPort(1199);
        return rmiServiceExporter;
    }

    /**
     * 装配RMI服务
     * @return
     */
    @Bean
    public RmiProxyFactoryBean rmiProxyFactoryBean(){
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1199/user1Service");
        rmiProxyFactoryBean.setServiceInterface(User1Service.class);
        return rmiProxyFactoryBean;
    }

}
