package com.vlad9pa.springcloud.eurekaclient.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Vlad Milyutin.
 */
//TODO: ADD RIBBON AUTO LOAD BALANCER
@Configuration
public class EurekaAutoServiceRegistration implements AutoServiceRegistration, SmartLifecycle, Ordered{

    private AtomicBoolean running = new AtomicBoolean(false);

    private int order = 0;

    private AtomicInteger port = new AtomicInteger(0);

    private ApplicationContext context;

    private EurekaServiceRegistry serviceRegistry;

    private EurekaRegistration registration;

    public EurekaAutoServiceRegistration(ApplicationContext context, EurekaServiceRegistry serviceRegistry, EurekaRegistration registration) {
        this.context = context;
        this.serviceRegistry = serviceRegistry;
        this.registration = registration;
    }

    @Override
    public void start() {

        if (this.port.get() != 0 && this.registration.getNonSecurePort() == 0) {
            this.registration.setNonSecurePort(this.port.get());
        }

        if (!this.running.get() && this.registration.getNonSecurePort() > 0) {

            this.serviceRegistry.register(this.registration);

            this.context.publishEvent(
                    new InstanceRegisteredEvent<>(this, this.registration.getInstanceConfig()));
            this.running.set(true);
        }
    }
    @Override
    public void stop() {
        this.serviceRegistry.deregister(this.registration);
        this.running.set(false);
    }

    @Override
    public boolean isRunning() {
        return this.running.get();
    }

    @Override
    public int getPhase() {
        return 0;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        stop();
        callback.run();
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @EventListener(EmbeddedServletContainerInitializedEvent.class)
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        // TODO: take SSL into account when Spring Boot 1.2 is available
        int localPort = event.getEmbeddedServletContainer().getPort();
        if (this.port.get() == 0) {
            this.port.compareAndSet(0, localPort);
            start();
        }
    }

    @EventListener(ContextClosedEvent.class)
    public void onApplicationEvent(ContextClosedEvent event) {
        stop();
    }


}
