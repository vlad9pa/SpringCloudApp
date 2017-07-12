package com.vlad9pa.springcloud.feign.builder;

import com.fasterxml.jackson.databind.JsonNode;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * Feign Client init
 *
 * Note:
 * If you want to autowire FeignClient  -- you need to use @RequestMapping!!!.
 * If you build FeignClient by Feign.builder() -- you need to use @RequestLine
 *
 * @author Vlad Milyutin.
 */
@Profile("FeignBuilder")
@FeignClient(name = "user-service")
public interface UserClient {


    @RequestLine("GET /")
    JsonNode getAllUsers();

    @RequestLine("GET /{username}")
    JsonNode getUserByUsername(@Param("username") String username);

    @RequestLine("POST /")
    @Headers("ContentType: application/json")
    JsonNode saveUser(@RequestBody JsonNode userData);

}
