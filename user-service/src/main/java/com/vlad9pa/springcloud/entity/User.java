package com.vlad9pa.springcloud.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Vlad Milytuin.
 */
@Data
@Document
public class User {

    @Id
    private String id;
    private String username;

}
