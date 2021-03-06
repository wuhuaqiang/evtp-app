package com.hhdl.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hhdl.common.model.GetEasyUIData;
import com.hhdl.system.dao.UserMapper;
import com.hhdl.system.dao.UserRoleMapper;
import com.hhdl.system.model.User;
import com.hhdl.system.model.UserAddModel;
import com.hhdl.system.model.UserPage;
import com.hhdl.system.model.UserRole;
import com.hhdl.system.service.ResourcesService;
import com.hhdl.system.service.UserService;
import com.hhdl.system.vo.UserVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private ResourcesService resourcesService;

    @Override
    @Transactional
    public void insertUser(UserAddModel user) {
        String[] roleId = user.getRoleId();
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        user.setId(UUID.randomUUID().toString());
        userMapper.insert(user);
        if (roleId == null) {
            return;
        }
        for (int i = 0; i < roleId.length; i++) {
            userRoleMapper.insert(new UserRole(UUID.randomUUID().toString()
                    , user.getId(), roleId[i]));
        }
    }

    @Override
    @Transactional
    public void updateUser(UserAddModel user) {
        String[] roleId = user.getRoleId();
        if (!"".equals(user.getPassword())) {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            userMapper.updateByPrimaryKey(user);
        }
        userMapper.updateNoPasswordByPrimaryKey(user);

        userRoleMapper.deleteByT(new UserRole[]{new UserRole(null, user.getId(), null)});


        if (roleId == null) {
            return;
        }
        for (int i = 0; i < roleId.length; i++) {
            userRoleMapper.insert(new UserRole(UUID.randomUUID().toString()
                    , user.getId(), roleId[i]));
        }
    }

    @Override
    @Transactional
    public void deleteUser(String[] id) {
        UserRole[] u = new UserRole[id.length];
        for (int i = 0; i < id.length; i++) {
            u[i] = new UserRole(null, id[i], null);
        }
        userRoleMapper.deleteByT(u);
        userMapper.deleteBy(id);

    }

    @Override
    public User selectByPrimaryKey(String id) {
        User u = userMapper.selectByPrimaryKey(id);
        u.setPassword("");
        return u;
    }

    @Override
    public void updatePassword(UserAddModel user) {
        if (!"".equals(user.getPassword())) {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            userMapper.updateByPrimaryKey(user);
        }
        userMapper.updateNoPasswordByPrimaryKey(user);
    }

    @Override
    public GetEasyUIData findTByPage(UserPage user) {
        Page<Object> objects = PageHelper.startPage(user.getPage(), user.getRows());
        return new GetEasyUIData(userMapper.findTByPage(user)
                , objects.getTotal());
    }


    @Override
    public UserVO selectVOByPrimaryKey(String id) {
        UserVO u = userMapper.selectVOByPrimaryKey(id);
        u.setPassword("");
        u.setUserRoleList(userRoleMapper.findTByT(new UserRole(null, id, null)));
        return u;
    }

    @Override
    public User findTByT(User user) {
        return userMapper.findTByT(user);
    }

    @Override
    public void deleteByT(UserRole[] u) {
        userRoleMapper.deleteByT(u);

    }
}
