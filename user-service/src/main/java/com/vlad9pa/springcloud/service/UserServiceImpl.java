package com.vlad9pa.springcloud.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vlad9pa.springcloud.entity.User;
import com.vlad9pa.springcloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vlad Milytuin.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public User save(JsonNode userData) {
        User user = new User();
        user.setUsername(((ObjectNode) userData).get("user_name").asText());

        userRepository.save(user);
        return user;
    }
}
