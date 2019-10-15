package com.hhdl.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hhdl.model.UserModel;
import org.apache.ibatis.annotations.Param;

/**
 * Created by linwf on 2018/11/26.
 */
public interface UserMapper extends BaseMapper<UserModel> {
    /**
     * 获取用户
     *
     * @param account  帐户
     * @param password 密码
     * @return
     */
    public UserModel getUser(@Param("account") String account, @Param("password") String password);

    /**
     * 修改密码
     *
     * @param password 密码
     * @return
     */
    public int modifyPassword(@Param("accout") String accout, @Param("password") String password);
}
