package com.vlad9pa.springcloud.eurekaclient.config;

import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vlad Milyutin.
 */
@Data
@ConfigurationProperties("eureka.instance")
public class EurekaInstanceConfigBean implements CloudEurekaInstanceConfig, EnvironmentAware {

    private static final String UNKNOWN = "unknown";

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private InetUtils.HostInfo hostInfo;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private InetUtils inetUtils;

    private String appname = UNKNOWN;

    private String appGroupName;

    private boolean instanceEnabledOnit;

    private int nonSecurePort = 80;

    private int securePort = 443;

    private boolean nonSecurePortEnabled = true;

    private boolean securePortEnabled;

    private int leaseRenewalIntervalInSeconds = 30;

    private int leaseExpirationDurationInSeconds = 90;

    private String virtualHostName = UNKNOWN;

    private String instanceId;

    private String secureVirtualHostName = UNKNOWN;

    private String aSGName;

    private Map<String, String> metadataMap = new HashMap<>();

    private DataCenterInfo dataCenterInfo = new MyDataCenterInfo(
            DataCenterInfo.Name.MyOwn);

    private String ipAddress;

    private String statusPageUrlPath = "/info";

    private String statusPageUrl;

    private String homePageUrlPath = "/";

    private String homePageUrl;

    private String healthCheckUrlPath = "/health";

    private String healthCheckUrl;

    private String secureHealthCheckUrl;

    private String namespace = "eureka";

    private String hostname;

    private boolean preferIpAddress = false;

    private InstanceInfo.InstanceStatus initialStatus = InstanceInfo.InstanceStatus.UP;

    private String[] defaultAddressResolutionOrder = new String[0];
    private Environment environment;

    public String getHostname() {
        return getHostName(false);
    }

    /* unused */
    private EurekaInstanceConfigBean() {
    }

    public EurekaInstanceConfigBean(InetUtils inetUtils) {
        this.inetUtils = inetUtils;
        this.hostInfo = this.inetUtils.findFirstNonLoopbackHostInfo();
        this.ipAddress = this.hostInfo.getIpAddress();
        this.hostname = this.hostInfo.getHostname();
    }

    @Override
    public String getInstanceId() {
        if (this.instanceId == null && this.metadataMap != null) {
            return this.metadataMap.get("instanceId");
        }
        return this.instanceId;
    }

    @Override
    public boolean getSecurePortEnabled() {
        return this.securePortEnabled;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
        this.hostInfo.override = true;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        this.hostInfo.override = true;
    }

    @Override
    public String getHostName(boolean refresh) {
        if (refresh && !this.hostInfo.override) {
            this.ipAddress = this.hostInfo.getIpAddress();
            this.hostname = this.hostInfo.getHostname();
        }
        return this.preferIpAddress ? this.ipAddress : this.hostname;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        RelaxedPropertyResolver springPropertyResolver = new RelaxedPropertyResolver(this.environment, "spring.application.");
        String springAppName = springPropertyResolver.getProperty("name");
        if(StringUtils.hasText(springAppName)) {
            setAppname(springAppName);
            setVirtualHostName(springAppName);
            setSecureVirtualHostName(springAppName);
        }
    }
}

