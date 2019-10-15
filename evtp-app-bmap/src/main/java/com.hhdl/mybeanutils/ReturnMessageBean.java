package com.hhdl.mybeanutils;


import java.util.ArrayList;
import java.util.List;


/**
 * 消息返回实体
 *
 * @author
 * @since 2018-4-19
 */
public class ReturnMessageBean {

    /**
     * 成功OR失败
     */
    private boolean result;
    /**
     * 处理完毕的返回说明信息
     */
    private String message;
    /**
     * 返回的数据集
     */
    private List<?> datas = new ArrayList();

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<?> getDatas() {
        return datas;
    }

    public void setDatas(List<?> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "ReturnMessageBean [result=" + result + ", message=" + message + ", datas=" + datas + "]";
    }


}
