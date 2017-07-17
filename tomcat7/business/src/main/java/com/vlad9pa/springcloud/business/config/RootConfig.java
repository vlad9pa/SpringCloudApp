package com.vlad9pa.springcloud.business.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Vlad Milyutin.
 */
@Configuration
@EnableWebMvc
@ComponentScan(value = "com.vlad9pa.springcloud.business")
@PropertySource(value = "classpath:/bootstrap.yml")
@EnableDiscoveryClient
public class RootConfig{

}
