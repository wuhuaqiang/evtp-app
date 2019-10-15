package com.hhdl.evtp.service;

import com.hhdl.evtp.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel getUser(String account, String password);

    List<UserModel> getAllUser();

    int modifyPassword(String account, String password);

    int addUser(UserModel userModel);

    int updateUser(UserModel userModel);

    int deleteUserById(int id);
}
