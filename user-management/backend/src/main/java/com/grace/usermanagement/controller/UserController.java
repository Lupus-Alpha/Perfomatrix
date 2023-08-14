package com.grace.usermanagement.controller;
import com.grace.usermanagement.dto.LoginRequest;

import com.grace.usermanagement.dto.RegisterRequest;
import com.grace.usermanagement.dto.ResultResponse;
import com.grace.usermanagement.service.impl.UserServiceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {
    @Autowired
    private UserServiceimpl userServices;

    @PostMapping("/login")
    public ResultResponse login(@RequestBody LoginRequest loginRequest) {
        //Subject currentUser = SecurityUtils.getSubject();
//        SessionUser sessionUser = SessionUtils.getUser();
//        if (sessionUser != null) {
//            if (!StringUtils.equals(sessionUser.getId(), request.getUsername())) {
//                return ResultHolder.error(Translator.get("please_logout_current_user"));
//            }
//        }
        //SecurityUtils.getSubject().getSession().setAttribute("authenticate", UserSource.LOCAL.name());
        return userServices.login(loginRequest);
    }

    @RequestMapping("register")
    public ResultResponse register(@RequestBody RegisterRequest registerRequest) {
        return userServices.register(registerRequest);
    }


}
