package com.grace.usermanagement.entity;

import lombok.Data;

@Data
public class Role {
    private String id;
    private String name;
    private String description;
    private long createTime;
    private long updateTime;
}
