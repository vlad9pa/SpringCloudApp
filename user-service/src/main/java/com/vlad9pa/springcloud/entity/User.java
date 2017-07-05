package com.vlad9pa.springcloud.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Vlad Milytuin.
 */
@Data
@Document
public class User {

    private String id;
    private String username;

}
