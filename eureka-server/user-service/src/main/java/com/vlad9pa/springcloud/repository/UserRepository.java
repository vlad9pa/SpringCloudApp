package com.vlad9pa.springcloud.repository;

import com.vlad9pa.springcloud.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vlad Milytuin.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAll();
    User findByUsername(String username);
}
