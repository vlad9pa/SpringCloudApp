package com.vlad9pa.springcloud.eurekaclient.config;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.cloud.netflix.eureka.*;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Minimal Configuration For Registration Instance
 * Copied from EurekaClientAutoConfiguration
 *
 * @author Vlad Milyutin.
 */
@Configuration
public class EurekaClientAutoConfiguration {

    private ConfigurableEnvironment env;
    @Autowired(required = false)
    private HealthCheckHandler healthCheckHandler;
    private RelaxedPropertyResolver propertyResolver;

    @Autowired
    public EurekaClientAutoConfiguration(ConfigurableEnvironment env) {
        this.env = env;
        this.propertyResolver = new RelaxedPropertyResolver(env);
    }

    @Bean
    @ConditionalOnMissingBean(value = EurekaClientConfig.class, search = SearchStrategy.CURRENT)
    public EurekaClientConfigBean eurekaClientConfigBean(){
        return new EurekaClientConfigBean();
    }

    @Bean
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) throws MalformedURLException {
        RelaxedPropertyResolver eurekaPropertyResolver = new RelaxedPropertyResolver(env, "eureka.instance.");
        String hostname = eurekaPropertyResolver.getProperty("hostname");
        String appname = eurekaPropertyResolver.getProperty("appname");

        boolean preferIpAddress = Boolean.parseBoolean(propertyResolver.getProperty("preferIpAddress"));
        int nonSecurePort = Integer.valueOf(propertyResolver.getProperty("server.port", eurekaPropertyResolver.getProperty("port", "8080")));
        int managementPort = Integer.valueOf(propertyResolver.getProperty("management.port", String.valueOf(nonSecurePort)));
        String managementContextPath = propertyResolver.getProperty("management.contextPath", eurekaPropertyResolver.getProperty("server.contextPath", "/"));
        EurekaInstanceConfigBean instance = new EurekaInstanceConfigBean(inetUtils);
        instance.setAppname(appname);
        instance.setNonSecurePort(nonSecurePort);
        instance.setInstanceId(IdUtils.getDefaultInstanceId(propertyResolver));
        instance.setPreferIpAddress(preferIpAddress);
        if (managementPort != nonSecurePort && managementPort != 0) {
            if (StringUtils.hasText(hostname)) {
                instance.setHostname(hostname);
            }
            String statusPageUrlPath = eurekaPropertyResolver.getProperty("statusPageUrlPath");
            String healthCheckUrlPath = eurekaPropertyResolver.getProperty("healthCheckUrlPath");
            if (!managementContextPath.endsWith("/")) {
                managementContextPath = managementContextPath + "/";
            }
            if (StringUtils.hasText(statusPageUrlPath)) {
                instance.setStatusPageUrlPath(statusPageUrlPath);
            }
            if (StringUtils.hasText(healthCheckUrlPath)) {
                instance.setHealthCheckUrlPath(healthCheckUrlPath);
            }
            String scheme = instance.getSecurePortEnabled() ? "https" : "http";
            URL base = new URL(scheme, instance.getHostname(), managementPort, managementContextPath);
            instance.setStatusPageUrl(new URL(base, StringUtils.trimLeadingCharacter(instance.getStatusPageUrlPath(), '/')).toString());
            instance.setHealthCheckUrl(new URL(base, StringUtils.trimLeadingCharacter(instance.getHealthCheckUrlPath(), '/')).toString());
        }
        return instance;
    }

    @Bean
    public DiscoveryClient discoveryClient(EurekaInstanceConfig config, EurekaClient client) {
        return new EurekaDiscoveryClient(config, client);
    }

    @Bean
    public EurekaServiceRegistry eurekaServiceRegistry() {
        return new EurekaServiceRegistry();
    }

    @Bean
    @ConditionalOnProperty(value = "spring.cloud.service-registry.auto-registration.enabled", matchIfMissing = true)
    public EurekaRegistration eurekaRegistration(EurekaClient eurekaClient, CloudEurekaInstanceConfig instanceConfig, ApplicationInfoManager applicationInfoManager) {
        return EurekaRegistration.builder(instanceConfig)
                .with(applicationInfoManager)
                .with(eurekaClient)
                .with(healthCheckHandler)
                .build();
    }

    @Bean
    @ConditionalOnProperty(value = "spring.cloud.service-registry.auto-registration.enabled", matchIfMissing = true)
    public EurekaAutoServiceRegistration eurekaAutoServiceRegistration(ApplicationContext context, EurekaServiceRegistry registry, EurekaRegistration registration) {
        return new EurekaAutoServiceRegistration(context, registry, registration);
    }

    @Bean
    public MutableDiscoveryClientOptionalArgs discoveryClientOptionalArgs() {
        return new MutableDiscoveryClientOptionalArgs();
    }

    @Configuration
    protected static class EurekaClientConfiguration {

        @Autowired
        private ApplicationContext context;

        @Autowired(required = false)
        private com.netflix.discovery.DiscoveryClient.DiscoveryClientOptionalArgs optionalArgs;

        @Bean(destroyMethod = "shutdown")
        @ConditionalOnMissingBean(value = EurekaClient.class, search = SearchStrategy.CURRENT)
        public EurekaClient eurekaClient(ApplicationInfoManager manager, EurekaClientConfig config) {
            return new CloudEurekaClient(manager, config, this.optionalArgs,
                    this.context);
        }

        @Bean
        @ConditionalOnMissingBean(value = ApplicationInfoManager.class, search = SearchStrategy.CURRENT)
        public ApplicationInfoManager eurekaApplicationInfoManager(
                EurekaInstanceConfig config) {
            InstanceInfo instanceInfo = new InstanceInfoFactory().create(config);
            return new ApplicationInfoManager(config, instanceInfo);
        }
    }

    @Bean
    public InetUtils inetUtils(){
        return new InetUtils(new InetUtilsProperties());
    }

}
