package com.hhdl.system.dao;

import com.hhdl.system.model.User;
import com.hhdl.system.model.UserAddModel;
import com.hhdl.system.model.UserPage;
import com.hhdl.system.vo.UserVO;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    User selectByPrimaryKey(String id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    List<User> findTByPage(UserPage user);

    int findTCountByT(UserPage user);

    void deleteBy(String[] id);

    User findTByT(User user);

    User findTByTOne(User user);

    void updateNoPasswordByPrimaryKey(UserAddModel user);

    UserVO selectVOByPrimaryKey(String id);


}