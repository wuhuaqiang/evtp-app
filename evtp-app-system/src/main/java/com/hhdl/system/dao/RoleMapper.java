package com.hhdl.system.dao;

import com.hhdl.system.model.Role;
import com.hhdl.system.model.RolePage;
import com.hhdl.system.vo.RoleVO;

import java.util.List;

public interface RoleMapper {

    int deleteByPrimaryKey(String id);

    int insert(Role record);

    Role selectByPrimaryKey(String id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    List<Role> findTByPage(RolePage role);

    int findTCountByT(RolePage role);

    void deleteBy(String[] id);

    RoleVO selectRoleVOByPrimaryKey(String id);

}