package com.vlad9pa.springcloud.feign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlad9pa.springcloud.entity.User;
import feign.Feign;
import feign.
import feign.jackson.JacksonDecoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 *
 * Feign Client usage.
 *
 * Maybe it can set ribbon client for load-balancer url..
 * https://github.com/OpenFeign/feign/tree/master/ribbon
 *
 * @author Vlad Milyutin.
 */
@RestController
public class Controller {

    private UserClient userClient;
    private ObjectMapper mapper;

    @PostConstruct
    public void init(){
        mapper = new ObjectMapper();
        userClient = Feign.builder().decoder(new JacksonDecoder()).target(UserClient.class, "http://localhost:8090");
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public User getUser(@PathVariable("username") String username) throws JsonProcessingException {
        JsonNode userData = userClient.getUserByUsername(username);
        User user = mapper.treeToValue(userData, User.class);
        return user;
    }


    @GetMapping(value = "/user/")
    public JsonNode getAllUsers(){
        return userClient.getAllUsers();
    }
}
