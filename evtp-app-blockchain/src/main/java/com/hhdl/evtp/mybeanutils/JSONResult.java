package com.hhdl.evtp.mybeanutils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

/**
 * 请求响应JSON对象
 */
public final class JSONResult implements Serializable {
    private static final long serialVersionUID = 1013123223232L;

    public JSONResult(ResultCode code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    ;

    public JSONResult(ResultCode code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    ;

    public JSONResult() {
    }

    ;
    /**
     * 返回状态码
     */
    private ResultCode code;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 返回数据
     */
    private Object data;
    /**
     * 返回jsessionid數據 ；
     */
    private String userRedisreQequestId;


    /**
     * 返回成功
     *
     * @return
     */
    public static JSONResult success() {
        return success(Constants.JSON_BACK_SUCCESS);
    }


    /**
     * 返回成功数据及信息
     *
     * @param msg
     * @param data
     * @return
     */
    public static JSONResult success(String msg, Object data) {
        return new JSONResult(ResultCode.success, msg, data);
    }

    /**
     * 返回成功数据
     *
     * @param data
     * @return
     */
    public static JSONResult success(Object data) {
        return new JSONResult(ResultCode.success, Constants.JSON_BACK_SUCCESS, data);
    }

    /**
     * 返回失败数据及信息
     *
     * @param msg
     * @param data
     * @return
     */
    public static JSONResult failure(String msg, Object data) {
        return new JSONResult(ResultCode.failure, msg, data);
    }

    /**
     * 返回失败数据及信息
     *
     * @param msg
     * @return
     */
    public static JSONResult lostCode(String msg) {
        return new JSONResult(ResultCode.lostCode, msg);
    }

    /**
     * 返回登录信息过期
     *
     * @param msg
     * @return
     */
    public static JSONResult expire(String msg) {
        return new JSONResult(ResultCode.expire, msg);
    }


    /**
     * 非法访问信息
     *
     * @param msg
     * @return
     */
    public static JSONResult illegal(String msg) {
        return new JSONResult(ResultCode.illegal, msg);
    }

    /**
     * 返回失败数据
     *
     * @param data
     * @return
     */
    public static JSONResult failure(Object data) {
        return new JSONResult(ResultCode.failure, Constants.JSON_BACK_FAILURE, data);
    }

    /**
     * 返回失败数据及信息
     *
     * @param msg
     * @return
     */
    public static JSONResult failure(String msg) {
        return new JSONResult(ResultCode.failure, msg);
    }


    public ResultCode getCode() {
        return code;
    }

    public String getUserRedisreQequestId() {
        return userRedisreQequestId;
    }

    public void setUserRedisreQequestId(String userRedisreQequestId) {
        this.userRedisreQequestId = userRedisreQequestId;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.WriteEnumUsingToString);
    }

    public static JSONResult last(String msg, Object data) {
        return new JSONResult(ResultCode.pageLastCode, msg, data);
    }
}
