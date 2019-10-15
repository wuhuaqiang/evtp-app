package com.hhdl.mybeanutils;

/**
 * 请求返回码
 */
public enum ResultCode {
    // 请求成功
    success("0"),
    // 请求失败
    failure("-1"),
    // 登录信息过期
    expire("-2"),
    // 请求非法
    illegal("-3"),
    // 登录验证码过期
    lostCode("-4"),
    //分页查询app端最后一条
    pageLastCode("-5"),
    // 服务器错误
    error("-6");

    private final String code;

    ResultCode(String state) {
        this.code = state;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return this.getCode();
    }
}
