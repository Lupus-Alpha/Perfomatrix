package com.grace.usermanagement.entity;

import lombok.Data;

@Data
public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private Long createTime;
    private Long updateTime;
    private String status;
    private String projectId;
    private String salt;
//    private String authenticate;

}
