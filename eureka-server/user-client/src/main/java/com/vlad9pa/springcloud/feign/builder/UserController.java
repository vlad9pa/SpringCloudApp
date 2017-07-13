package com.vlad9pa.springcloud.feign.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.ribbon.RibbonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * Using FeignClient with Feign.builder() and RibbonClient
 *
 *
 * @author Vlad Milyutin.
 */
@Profile("FeignBuilder")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RibbonClient ribbonClient;

    private UserClient userClient;

    @PostConstruct
    public void init(){
        userClient = Feign.builder()
                .decoder(new JacksonDecoder()).encoder(new JacksonEncoder())
                .client(ribbonClient)
                .target(UserClient.class, "http://ribbon-user-service/user");
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public JsonNode getUser(@PathVariable("username") String username) throws JsonProcessingException {
        JsonNode userData = userClient.getUserByUsername(username);
        return userData;
    }

    @GetMapping(value = "/")
    public JsonNode getAllUsers(){
        return userClient.getAllUsers();
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode saveUser(@RequestBody JsonNode userData){
        userData = userClient.saveUser(userData);
        return userData;
    }


    @Bean
    public RibbonClient ribbonClient(){
        return new RibbonClient();
    }


}
