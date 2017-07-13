# SPRING CLOUD DEMO
This project was created for learning Spring Cloud.

# EUREKA-SERVER/USER-CLIENT

USER-CLIENT have 3 profiles:
* Ribbon
* FeignAutowire
* FeignBuilder

#### Eureka Ribbon LoadBalancer:
For using ribbon http client with rest template.
<p> Example in 
<a href="https://github.com/vlad9pa/SpringCloudApp/blob/master/eureka-server/user-client/src/main/java/com/vlad9pa/springcloud/ribbon/controller/UserController.java"> eureka-server/user-client/ribbon </a>

#### FeignAutowire:
For using Feign http client with @Autowired.
<p> Example in 
<a href="https://github.com/vlad9pa/SpringCloudApp/tree/master/eureka-server/user-client/src/main/java/com/vlad9pa/springcloud/feign/autowire"> eureka-server/user-client/feign/autowire </a>

#### FeignBuilder:
For using Feign http client with Feign.builder() with ribbon load balancer
<p> Example in 
<a href="https://github.com/vlad9pa/SpringCloudApp/tree/master/eureka-server/user-client/src/main/java/com/vlad9pa/springcloud/feign/builder"> eureka-server/user-client/feign/builder </a>


# Ribbon-LoadBalancer

Simple using Ribbon Load Balancing with RestTemplate

<p> Example in <a href="https://github.com/vlad9pa/SpringCloudApp/tree/master/ribbon-loadbalancer"> Ribbon-LoadBalancer </a>
