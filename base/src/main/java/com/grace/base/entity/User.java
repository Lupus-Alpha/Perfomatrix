package com.grace.base.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String Id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String projectId;
    private String status;
    private Long createTime;
    private Long updateTime;
    private static final long serialVersionUID = 1L;

}
