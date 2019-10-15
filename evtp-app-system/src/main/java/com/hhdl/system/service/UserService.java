package com.hhdl.system.service;

import com.hhdl.common.model.GetEasyUIData;
import com.hhdl.system.model.User;
import com.hhdl.system.model.UserAddModel;
import com.hhdl.system.model.UserPage;
import com.hhdl.system.model.UserRole;
import com.hhdl.system.vo.UserVO;

public interface UserService  {

    User selectByPrimaryKey(String id);

    void insertUser(UserAddModel muser);

    void updateUser(UserAddModel muser);

    void deleteUser(String[] id);

    void updatePassword(UserAddModel u);

    GetEasyUIData findTByPage(UserPage user);

    UserVO selectVOByPrimaryKey(String id);

    User findTByT(User user);

    void deleteByT(UserRole[] u);


}
