package com.vlad9pa.springcloud.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Vlad Milyutin.
 */

@Data
@Document
public class User {
    @Id
    String id;
    String username;
    String password;
    String role;


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
