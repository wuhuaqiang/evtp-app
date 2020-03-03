package com.hhdl.util;

import com.alibaba.fastjson.JSONObject;
import com.hhdl.common.model.CommonResult;
import com.hhdl.entity.HttpClientResult;
import com.hhdl.model.EvtpElectricVehicle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockChainUtils {
    private static final String BlockChainBaseUrl = "http://10.168.1.245:8089/api/";
    private static final String InvokeUrl = "chaincode/invoke";
    private static final String QueryUrl = "chaincode/query";

    public static CommonResult queryBlockChainInfo(String url, Map<String, String> params, String sessionId) throws Exception {
        HttpClientResult httpClientResult = HttpClientUtils.doPostInfo(url, JSONObject.toJSONString(params), sessionId);
        return CommonResult.success(httpClientResult.getContent());
    }
    /**
     * @param args      有序参数集合
     * @param fcnName   需要执行的智能合约方法
     * @param sessionId 当前sessionId
     * @param type      查询还是写入
     * @return
     * @throws Exception
     */
    public static CommonResult executeBlockChain(List<String> args, String fcnName, String sessionId, String type) throws Exception {
        String url = "";
        if ("invoke".equals(type)) {
            url = BlockChainBaseUrl + InvokeUrl;
        } else {
            url = BlockChainBaseUrl + QueryUrl;
        }
        Map<String, String> params = new HashMap<>();
        String argsStr = JSONObject.toJSONString(args);
        params.put("fcn", fcnName);
        params.put("args", argsStr);
        CommonResult httpClientResult = queryBlockChainInfo(url, params, sessionId);
        return httpClientResult;
    }

//    public static CommonResult invokeBlockChain(String url, Map<String, String> params, String sessionId) throws Exception {
//        Map<String, String> headers = new HashMap<>();
//        HttpClientResult httpClientResult = HttpClientUtils.doPost(BlockChainBaseUrl + url, JSONObject.toJSONString(params),sessionId);
//        return CommonResult.success(httpClientResult.getContent());
//    }
}
