package com.vlad9pa.springcloud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vlad Milyutin.
 */


@RestController
public class Contoller {

    @GetMapping(value = "/hello")
    public String hello(){
        return "HELLO WORLD";
    }
}
