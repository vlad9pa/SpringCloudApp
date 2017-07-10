package com.vlad9pa.springcloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 *
 * Resource server for verifying token.
 *
 * @author Vlad Milyutin.
 */
@Configuration
@EnableResourceServer
@PropertySource("classpath:/resource-server.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${token.check.endpoint}")
    private String tokenCheckEndPoint;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/uaa/uaa/oauth/token").permitAll().anyRequest().authenticated()
                .and()
                .csrf().disable()
                .addFilterAfter(oAuth2AuthenticationProcessingFilter(), AbstractPreAuthenticatedProcessingFilter.class);
    }

    private OAuth2AuthenticationProcessingFilter oAuth2AuthenticationProcessingFilter() {
        OAuth2AuthenticationProcessingFilter oAuth2AuthenticationProcessingFilter =
                new OAuth2AuthenticationProcessingFilter();
        oAuth2AuthenticationProcessingFilter.setAuthenticationManager(authenticationManager());
        oAuth2AuthenticationProcessingFilter.setStateless(true);

        return oAuth2AuthenticationProcessingFilter;
    }


    @Bean
    @Primary
    public RemoteTokenServices tokenService() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();

        tokenServices.setCheckTokenEndpointUrl(tokenCheckEndPoint);
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

}
