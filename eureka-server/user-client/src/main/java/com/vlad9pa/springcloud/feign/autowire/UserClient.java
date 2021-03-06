package com.vlad9pa.springcloud.feign.autowire;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * Feign Client example for Autowiring.
 *
 * Note:
 * If you want to autowire FeignClient  -- you need to use @RequestMapping!!!
 *
 * @author Vlad Milyutin.
 */
@Profile("FeignAutowire")
@FeignClient(name = "user-service")
public interface UserClient{

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    JsonNode getAllUsers();

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    JsonNode getUserByUsername(@PathVariable("username") String username);

    @RequestMapping(value = "/user/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    JsonNode saveUser(@RequestBody JsonNode userData);
}
