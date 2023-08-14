package com.grace.usermanagement.config;


import com.grace.usermanagement.dto.UserDTO;
import com.grace.usermanagement.entity.Role;
import com.grace.usermanagement.service.impl.UserServiceimpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserServiceimpl userService;

    private Logger logger = LoggerFactory.getLogger(UserRealm.class);


    // 权限验证
    // 根据用户身份信息获取该用户的角色和权限信息，并将其封装到AuthorizationInfo对象中返回
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userId = (String) principalCollection.getPrimaryPrincipal();
        return getAuthorizationInfo(userId, userService);
    }
    public static AuthorizationInfo getAuthorizationInfo(String userId, UserServiceimpl userService) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // roles 内容填充
        UserDTO userDTO = userService.getUserDTO(userId);
        Set<String> roles = userDTO.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        authorizationInfo.setRoles(roles);
        return authorizationInfo;
    }


    // 登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //String login = (String) SecurityUtils.getSubject().getSession().getAttribute("authenticate");

        String userId = token.getUsername();
        String password = String.valueOf(token.getPassword());

        return loginAuth(userId, password);


    }

    private AuthenticationInfo loginAuth(String userId, String password) {
        UserDTO user = userService.getUserDTO(userId);
        String msg;
        if (user == null) {

            msg = "The user does not exist: " + userId;
            logger.warn(msg);
            throw new UnknownAccountException("user does not exist");
        }

        // 密码验证
        if (!userService.checkUserPassword(userId, password)) {
            throw new IncorrectCredentialsException("password_is_incorrect");
        }
//        SessionUser sessionUser = SessionUser.fromUser(user);
//        SessionUtils.putUser(sessionUser);
        return new SimpleAuthenticationInfo(userId, password, getName());
    }
}
