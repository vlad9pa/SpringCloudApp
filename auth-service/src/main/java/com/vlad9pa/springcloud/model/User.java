package com.vlad9pa.springcloud.model;

import lombok.Data;

import java.util.List;

/**
 * @author Vlad Milyutin.
 */
@Data
public class User {

    private String id;

    private String username;

    private String password;

    private List<String> roles;

}
