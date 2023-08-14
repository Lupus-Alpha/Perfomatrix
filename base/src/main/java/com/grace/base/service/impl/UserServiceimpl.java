package com.grace.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.grace.base.dto.UserDTO;
import com.grace.base.entity.User;
import com.grace.base.entity.UserRole;
import com.grace.base.mapper.userMapper;
import com.grace.base.mapper.userRoleMapper;
import com.grace.base.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceimpl implements UserService {

    @Autowired
    private userMapper userMapper;
    @Autowired
    private userRoleMapper userRoleMappers;

    public List<UserRole> getUserRoles(String userId){
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);

        List<UserRole> userRoleList = userRoleMappers.selectList(queryWrapper);

        return userRoleList;
    }

    public UserDTO getUserDTO(String userId){
        UserDTO userDTO = new UserDTO();

        User user = userMapper.selectById(userId);
        if(user == null){
            return null;
        }
        BeanUtils.copyProperties(user,userDTO);
        List<UserRole> userRoles = getUserRoles(userId);
        userDTO.setUserRoles(Optional.ofNullable(userRoles).orElse(new ArrayList<>()));
        return userDTO;



    }
}
