package com.falcon.furniture.furniture.dao;

import com.falcon.furniture.furniture.model.Role;

import java.util.Optional;

public interface RoleDao {
    Optional<Role> findByName(String name);
    void addRole(Role role);
    Role findByRoleId(String roleId);
}
