package com.vlad9pa.springcloud.config;

import com.vlad9pa.springcloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import javax.ws.rs.HttpMethod;

/**
 * @author Vlad Milyutin.
 */
@Configuration
@EnableResourceServer
@EnableWebSecurity
@PropertySource(value = {"classpath:resourcefilter.properties"})
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{


    @Value("${checkTokenEndpointUrl}")
    private String checkTokenEndpointUrl;

    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;



    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("user").roles("ROLE");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer configurer) {
        configurer.tokenServices(tokenService());
    }


    @Bean
    @Primary
    public RemoteTokenServices tokenService() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();

        tokenServices.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
        tokenServices.setClientId(clientId);
        tokenServices.setClientSecret(clientSecret);

        return tokenServices;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        OAuth2AuthenticationManager manager = new OAuth2AuthenticationManager();
        manager.setTokenServices(tokenService());
        return manager;
    }




    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable();
    }

}

