package com.vlad9pa.springcloud.presentation.client;

import com.netflix.client.ClientFactory;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.niws.client.http.HttpClientRequest;
import com.netflix.niws.client.http.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;

/**
 * @author Vlad Milyutin.
 */
@Component
public class LoadBalancer {

    @PostConstruct
    public void init() throws Exception {
        ConfigurationManager.loadPropertiesFromResources("bootstrap.properties");
    }

    @Autowired
    private SpringClientFactory springClientFactory;

    @Bean
    public SpringClientFactory springClientFactory(){
        return new SpringClientFactory();
    }

    @Bean
    public RibbonLoadBalancerClient loadBalancerClient(){
        return new RibbonLoadBalancerClient(springClientFactory);
    }


    @Bean
    public PropertiesFactory propertiesFactory(){
        return new PropertiesFactory();
    }
}
