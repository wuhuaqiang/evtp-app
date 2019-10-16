package com.hhdl.evtp.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hhdl.evtp.model.UserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    public List<UserModel> getUserByAccount(@Param("account") String account);

    public UserModel getUserById(@Param("row_id") int row_id);

    /**
     * 获取用户列表
     *
     * @return
     */
    public List<UserModel> getAllUser();

    /**
     * 修改密码
     *
     * @param password 密码
     * @return
     */
    public int modifyPassword(@Param("accout") String accout, @Param("password") String password);

    /**
     * 添加用户
     *
     * @param userModel
     * @return
     */
    public int addUser(UserModel userModel);

    /**
     * 更新用户
     *
     * @param userModel
     * @return
     */
    public int updateUser(UserModel userModel);
}
