/*
package com.vlad9pa.springcloud.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.vlad9pa.springcloud.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

*/
/**
 * @author Vlad Milytuin.
 *//*

@FeignClient("users")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers();

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getByUserName(@PathVariable("userName") String userName);

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User saveUser(@RequestBody JsonNode userData);
}
*/
