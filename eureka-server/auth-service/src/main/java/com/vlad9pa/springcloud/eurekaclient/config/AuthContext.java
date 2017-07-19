package com.vlad9pa.springcloud.eurekaclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author Vlad Milyutin.
 */
@Configuration
@PropertySource(value = {"classpath:application.properties"})
public class AuthContext {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.password}")
    private String redisPassword;

    @Bean
    @DependsOn("redisConnectionFactory")
    public TokenStore tokenStore(){
        return new RedisTokenStore(redisConnectionFactory());
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();

        connectionFactory.setHostName(redisHost);
        connectionFactory.setPort(redisPort);
        connectionFactory.setPassword(redisPassword);
        connectionFactory.setUsePool(true);

        return connectionFactory;
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
