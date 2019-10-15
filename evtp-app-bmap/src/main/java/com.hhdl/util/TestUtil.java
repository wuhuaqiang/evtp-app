package com.hhdl.util;

import com.alibaba.fastjson.JSONObject;
import com.hhdl.common.model.CommonResult;
import com.hhdl.entity.HttpClientResult;
import com.hhdl.mybeanutils.MyBeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtil {
    public static void main(String[] args) throws Exception {
        List<String> list = new ArrayList<>();
        list.add("sadasdl");
        list.add("asdasd");
        list.add("asdsad");
        list.add("qew");
        list.add("qwvzdx");
        String s = JSONObject.toJSONString(list);
        System.out.println(s);
//        Map<String, String> chargingInfo = getChargingInfo(3.85, 24.0, 5.0);
//        System.out.println(chargingInfo);
//        Map<String, String> params = new HashMap<>();
//        params.put("args", "[\"a\"]");
//        params.put("fcn", "queryAccount");
//        CommonResult commonResult = BlockChainUtils.queryBlockChainInfo("chaincode/query", params);
//        System.out.println(commonResult.getData());
    }

    private static Map<String, String> getChargingInfo(double currPower, double batteryCapacity, double minChargingNum) throws Exception {
        double randomNumber = Match.getRandomNumber(minChargingNum, batteryCapacity - currPower);
        System.currentTimeMillis();
        Map<String, String> result = new HashMap<>();
        Map param = new HashMap();
        param.put("output", "json");
        param.put("n1", String.valueOf(currPower));//时间
        param.put("n2", String.valueOf(randomNumber));
        System.out.println(param);
        HttpClientResult httpClientResult = HttpClientUtils.doGet("http://10.168.1.151:8000/getSoc", param);
        JSONObject jsonObject = JSONObject.parseObject(httpClientResult.getContent());
        Map<String, Object> stringObjectMap = MyBeanUtils.bean2map(jsonObject);
        Map<String, String> innerMap = (Map<String, String>) stringObjectMap.get("innerMap");
        String realSOCChange = innerMap.get("realSOCChange");
        String chargeTime = innerMap.get("chargeTime");
        String finalPay = innerMap.get("finalPay");
        result.put("chargeTime", chargeTime);
        result.put("endPower", String.valueOf(ArithUtil.add(Double.valueOf(realSOCChange), currPower)));
        result.put("finalPay", finalPay);
        return result;
    }
}
