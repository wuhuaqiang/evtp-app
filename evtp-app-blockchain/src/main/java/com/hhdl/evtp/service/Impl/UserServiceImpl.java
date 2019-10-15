package com.hhdl.evtp.service.Impl;

import com.hhdl.evtp.dao.UserMapper;
import com.hhdl.evtp.model.UserModel;
import com.hhdl.evtp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserModel getUser(String account, String password) {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        return userMapper.getUser(account, md5Password);
    }

    @Override
    public List<UserModel> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    public int deleteUserById(int id) {
        UserModel userModel = userMapper.getUserById(id);
        userModel.setIs_delete(true);
        return this.updateUser(userModel);
    }

    @Override
    public int modifyPassword(String account, String password) {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        return userMapper.modifyPassword(account, md5Password);
    }

    @Override
    public int addUser(UserModel userModel) {
        String md5Password = DigestUtils.md5DigestAsHex(userModel.getPassword().getBytes());
        userModel.setPassword(md5Password);
        return userMapper.addUser(userModel);
    }

    @Override
    public int updateUser(UserModel userModel) {
        return userMapper.updateUser(userModel);
    }
}
