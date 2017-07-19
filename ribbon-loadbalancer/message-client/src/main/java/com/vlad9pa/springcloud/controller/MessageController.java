package com.vlad9pa.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vlad Milyutin.
 */
@RestController
public class MessageController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hi")
    public String hi(@RequestParam(name = "name", defaultValue = "Someone")  String name){
        String helloMessage = restTemplate.getForObject("http://message-service/hello", String.class);
        return String.format("%s, %s", helloMessage, name);
    }
}
