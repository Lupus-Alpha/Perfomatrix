package com.grace.usermanagement.service;
import com.grace.usermanagement.dto.LoginRequest;
import com.grace.usermanagement.dto.RegisterRequest;
import com.grace.usermanagement.dto.ResultResponse;
import com.grace.usermanagement.dto.UserDTO;

public interface UserService {
    public UserDTO getUserDTO(String userId);

    public boolean checkUserPassword(String userId, String password);

    public ResultResponse login(LoginRequest request);

    public ResultResponse register(RegisterRequest request);

}
