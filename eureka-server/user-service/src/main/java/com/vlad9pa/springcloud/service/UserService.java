package com.vlad9pa.springcloud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vlad9pa.springcloud.entity.User;

import java.util.List;

/**
 * @author Vlad Milytuin.
 */
public interface UserService{
    List<User> findAll();
    User findByUsername(String userName);
    User save(JsonNode userData) throws JsonProcessingException;
}
