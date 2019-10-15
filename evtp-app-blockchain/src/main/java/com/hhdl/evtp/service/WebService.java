package com.hhdl.evtp.service;

import com.alibaba.fastjson.JSONObject;
import com.hhdl.evtp.model.UserModel;

/**
 * Created by linwf on 2018/11/26.
 */
public interface WebService {
    UserModel getUser(String account, String password);

    int save(JSONObject json);

    int modifyPassword(String account, String password);

    int addUser(UserModel userModel);
}
