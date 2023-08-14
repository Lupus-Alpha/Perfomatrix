package com.grace.base.dto;

import com.grace.base.entity.Role;
import com.grace.base.entity.User;
import com.grace.base.entity.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO extends User {
    private List<Role> roles;
    private List<UserRole> userRoles;
    private static final long serialVersionUID = 1L;
}
