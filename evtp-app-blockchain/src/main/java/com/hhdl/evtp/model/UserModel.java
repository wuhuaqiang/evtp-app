package com.hhdl.evtp.model;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

@TableName("fabric_use")
public class UserModel implements Serializable {
    private static final long serialVersionUID = -8366929034564774130L;
    private int row_id;  //序号
    private String name; //用户名
    private String account; //帐户
    private String password; //密码
    private int league_id; //配置id
    private boolean is_delete;//是否删除
    private String user_info_id;

    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public boolean isIs_delete() {
        return is_delete;
    }

    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }

    public int getLeague_id() {
        return league_id;
    }

    public void setLeague_id(int league_id) {
        this.league_id = league_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_info_id() {
        return user_info_id;
    }

    public void setUser_info_id(String user_info_id) {
        this.user_info_id = user_info_id;
    }
}
