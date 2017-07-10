package com.vlad9pa.springcloud.controller;

import com.vlad9pa.springcloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * Testing Ribbon Client.
 *
 *
 * @author Vlad Milyutin.
 */

@RestController
@RequestMapping("/users")
public class UserController {

    @LoadBalanced
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    User getByUserName(@PathVariable("username") String username){
       User user = restTemplate.getForObject("http://user-service/user/"+username, User.class);
       return user;
    }
}
