package com.vlad9pa.springcloud.feign;

import com.fasterxml.jackson.databind.JsonNode;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author Vlad Milyutin.
 */
@FeignClient(serviceId = "user-service", name = "users")
public interface UserClient{

    @RequestLine("GET /user/")
    JsonNode getAllUsers();

    @RequestLine("GET /user/{username}")
    JsonNode getUserByUsername(@Param("username") String username);
}
