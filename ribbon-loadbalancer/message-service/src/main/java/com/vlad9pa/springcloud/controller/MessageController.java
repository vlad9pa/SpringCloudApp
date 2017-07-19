package com.vlad9pa.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vlad Milyutin.
 */
@RestController
public class MessageController {

    @GetMapping(value = "/hello")
    public String hello(){
        return "Hello";
    }

}
