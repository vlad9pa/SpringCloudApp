package com.vlad9pa.springcloud.business.config;

import com.vlad9pa.springcloud.eurekaclient.config.EurekaClientAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/**
 * @author Vlad Milyutin.
 */
@Configuration
@EnableWebMvc
@ComponentScan(value = "com.vlad9pa.springcloud.business")
@PropertySource("classpath:/application.properties")
@EnableDiscoveryClient
public class RootConfig extends EurekaClientAutoConfiguration{

    public RootConfig(ConfigurableEnvironment env) {
        super(env);
    }
}
