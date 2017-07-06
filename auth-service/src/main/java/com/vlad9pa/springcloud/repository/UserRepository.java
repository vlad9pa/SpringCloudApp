package com.vlad9pa.springcloud.repository;

import com.vlad9pa.springcloud.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vlad Milyutin.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String>{
    User findByUsername(String username);
}
