package com.hhdl.system.dao;

import com.hhdl.system.model.User;
import com.hhdl.system.model.UserRole;

import java.util.List;

public interface UserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserRole record);

    UserRole selectByPrimaryKey(String id);

    List<UserRole> selectAll();

    int updateByPrimaryKey(User record);

    void deleteByT(UserRole[] u);

    List<UserRole> findTByT(UserRole userRole);
}