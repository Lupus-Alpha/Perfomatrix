package com.grace.base.config;

import com.grace.base.dto.UserDTO;
import com.grace.base.entity.Role;
import com.grace.base.service.impl.UserServiceimpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;
import java.util.stream.Collectors;

public class UserRealm extends AuthorizingRealm {
    private UserServiceimpl userService;

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
        return null;
    }
}
