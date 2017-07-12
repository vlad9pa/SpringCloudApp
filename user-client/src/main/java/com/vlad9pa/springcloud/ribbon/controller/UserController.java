package com.vlad9pa.springcloud.ribbon.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlad9pa.springcloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.http.HttpMethod.POST;

/**
 *
 * Using Ribbon Client.
 *
 *
 * @author Vlad Milyutin.
 */
@Profile("Ribbon")
@RestController
@RequestMapping("/user")
public class UserController {

    private ObjectMapper objectMapper;

    @PostConstruct
    public void init(){
        objectMapper = new ObjectMapper();
    }

    @LoadBalanced
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getByUserName(@PathVariable("username") String username){
       User user = restTemplate.getForObject("http://user-service/user/"+username, User.class);
       return user;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(){
        List<User> userList = restTemplate.getForObject("http://user-service/user/", List.class);
        return userList;
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveUser(@RequestBody JsonNode userData){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(userData.toString(),headers);

        ResponseEntity<String> response = restTemplate.exchange("http://user-service/user/", POST, entity, String.class);
        return response;
    }
}
