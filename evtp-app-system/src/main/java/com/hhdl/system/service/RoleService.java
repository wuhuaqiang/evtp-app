package com.hhdl.system.service;

import com.hhdl.common.model.GetEasyUIData;
import com.hhdl.system.model.*;
import com.hhdl.system.vo.RoleVO;

import java.util.List;

public interface RoleService {

    void insertRole(RoleAddModel role);

    void updateRole(RoleAddModel role);

    void deleteRole(String[] id);

    GetEasyUIData findTByPage(RolePage role);

    RoleVO selectByPrimaryKey(String id);

    List<Role> selectAll();

    void deleteByT(RoleResources[] r);

    List<Resources> findResourcesByT(Resources resources);

}
