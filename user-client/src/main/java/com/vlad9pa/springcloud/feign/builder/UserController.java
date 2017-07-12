package com.vlad9pa.springcloud.feign.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * Using FeignClient with FeignBuilder
 *
 *
 * Maybe it can set ribbon client for load-balancer url..
 * https://github.com/OpenFeign/feign/tree/master/ribbon
 *
 * @author Vlad Milyutin.
 */
@Profile("FeignBuilder")
@RestController
@RequestMapping("/user")
public class UserController {

    private UserClient userClient;

    @PostConstruct
    public void init(){
        userClient = Feign.builder().decoder(new JacksonDecoder()).target(UserClient.class, "http://localhost:8808/user");
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


    // Can't decode JsonNode:
    // feign.codec.EncodeException: class com.fasterxml.jackson.databind.node.ObjectNode is not a type supported by this encoder.
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode saveUser(@RequestBody JsonNode userData){

        userData = userClient.saveUser(userData);
        return userData;
    }
}
