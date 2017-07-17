package com.vlad9pa.springcloud.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vlad Milyutin.
 */
@RestController
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "Name") String name){
        String res = restTemplate.getForObject("http://business-service/hello", String.class);
        return String.format("{ %s, %s }", res, name);
    }

}
