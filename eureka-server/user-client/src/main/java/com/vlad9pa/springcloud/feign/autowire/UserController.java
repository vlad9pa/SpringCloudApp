package com.vlad9pa.springcloud.feign.autowire;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 *
 * Feign Client usage with Autowire.
 *
 * @author Vlad Milyutin.
 */
@Profile("FeignAutowire")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserClient userClient;

    @Autowired
    public UserController(UserClient userClient) {
        this.userClient = userClient;
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
}
