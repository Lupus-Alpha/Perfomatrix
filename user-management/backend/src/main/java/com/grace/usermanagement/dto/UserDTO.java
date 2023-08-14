package com.grace.usermanagement.dto;


import com.grace.usermanagement.entity.Role;
import com.grace.usermanagement.entity.User;
import com.grace.usermanagement.entity.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO extends User {
    private List<Role> roles;
    private List<UserRole> userRoles;
    private static final long serialVersionUID = 1L;
}
