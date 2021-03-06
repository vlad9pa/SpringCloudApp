package com.vlad9pa.springcloud.presentation.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Vlad Milyutin.
 */
@EnableWebMvc
@ComponentScan("com.vlad9pa.springcloud.presentation")
@RibbonClient(name = "business-service")
@EnableAutoConfiguration
@EnableDiscoveryClient
@PropertySource("classpath:/bootstrap.properties")
public class RootConfig {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
