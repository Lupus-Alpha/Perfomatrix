package com.grace.usermanagement.controller;


import com.grace.base.dto.ResultResponse;
import com.grace.usermanagement.dto.LoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController {

    @PostMapping("/login")
    public ResultResponse login(@RequestBody LoginRequest loginRequest) {

        return null;
    }

}
