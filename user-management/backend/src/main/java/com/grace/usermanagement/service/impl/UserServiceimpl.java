package com.grace.usermanagement.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.grace.usermanagement.dto.LoginRequest;
import com.grace.usermanagement.dto.RegisterRequest;
import com.grace.usermanagement.dto.ResultResponse;
import com.grace.usermanagement.dto.UserDTO;
import com.grace.usermanagement.entity.User;
import com.grace.usermanagement.entity.UserRole;
import com.grace.usermanagement.mapper.UserMapper;
import com.grace.usermanagement.mapper.UserRoleMapper;
import com.grace.usermanagement.service.UserService;
import com.grace.usermanagement.util.SaltUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceimpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMappers;

    public List<UserRole> getUserRoles(String userId){
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);

        List<UserRole> userRoleList = userRoleMappers.selectList(queryWrapper);

        return userRoleList;
    }

    public UserDTO getUserDTO(String username){
        UserDTO userDTO = new UserDTO();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);

        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            return null;
        }
        BeanUtils.copyProperties(user,userDTO);
       // List<UserRole> userRoles = getUserRoles(username);
       // userDTO.setUserRoles(Optional.ofNullable(userRoles).orElse(new ArrayList<>()));
        return userDTO;



    }

    @Override
    public boolean checkUserPassword(String username, String password) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
        if(user != null){
            String salt = user.getSalt();
            Md5Hash md5Hash = new Md5Hash(password,salt,1024);
            if(user.getPassword().equals(md5Hash.toHex())){
                return true;
            }
        }
        return false;
    }

    public ResultResponse login(LoginRequest request) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(request.getUsername(), request.getPassword());
        token.setRememberMe(true);
        try {
            currentUser.login(token);
        } catch (Exception e) {
            return new ResultResponse("201", "login failed");
        }

        return new ResultResponse("200", "login success",currentUser.getSession().getAttribute("user"));

    }

    @Override
    public ResultResponse register(RegisterRequest request) {
        String username = request.getUsername();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
        if(user != null){
            return new ResultResponse("201", "username already exists");
        }
        String salt = SaltUtils.getSalt(8);
        Md5Hash md5Hash = new Md5Hash(request.getPassword(),salt,1024);
        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setUsername(username);
        newUser.setSalt(salt);
        newUser.setPassword(md5Hash.toHex());
        newUser.setEmail(request.getEmail());
        newUser.setCreateTime(System.currentTimeMillis());
        newUser.setUpdateTime(System.currentTimeMillis());
        userMapper.insert(newUser);
        return new ResultResponse("200", "register success");

    }
}
