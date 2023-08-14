package com.grace.usermanagement.dto;

import lombok.Data;

@Data
public class LoginRequest {

        private String username;
        private String password;
        private String authenticate;
}
