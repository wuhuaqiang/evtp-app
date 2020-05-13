package com.hhdl.util;

import com.alibaba.fastjson.JSONObject;
import com.hhdl.common.model.Offer;
import com.hhdl.entity.HttpClientResult;
import com.hhdl.mybeanutils.MyBeanUtils;

import java.util.HashMap;
import java.util.Map;

public class ElectricVehiclePowerUtil {
    public static Map<String, String> getChargingInfo(double currPower, double batteryCapacity, double minChargingNum, String userId) throws Exception {
        double randomNumber = Match.getRandomNumber(minChargingNum, batteryCapacity - currPower);
        System.currentTimeMillis();
        Map<String, String> result = new HashMap<>();
        Map param = new HashMap();
        param.put("output", "json");
        param.put("n1", String.valueOf(currPower));//时间
        param.put("n2", String.valueOf(randomNumber));
        param.put("n3", userId);
        HttpClientResult httpClientResult = HttpClientUtils.doGet("http://10.168.1.213:9001/chargeTest", param);
        JSONObject jsonObject = JSONObject.parseObject(httpClientResult.getContent());
        Map<String, Object> stringObjectMap = MyBeanUtils.bean2map(jsonObject);
        Map<String, String> innerMap = (Map<String, String>) stringObjectMap.get("innerMap");
        String realSOCChange = innerMap.get("realSOCChange");
        String chargeTime = innerMap.get("chargeTime");
        String startTime = innerMap.get("startTime");
        String finalPay = innerMap.get("finalPay");
        result.put("startTime", startTime);
        result.put("chargeTime", chargeTime);
        result.put("endPower", String.valueOf(Double.valueOf(realSOCChange) + currPower));
        result.put("finalPay", finalPay);
        result.put("realSOCChange", realSOCChange);
        return result;
    }

    public static Map<String, String> setDisChargingInfo(double currPower, double totalPowerConsumption, double totalTime, Integer totalPoints, String userId) throws Exception {
//        double randomNumber = Match.getRandomNumber(minChargingNum, batteryCapacity - currPower);
        System.currentTimeMillis();
        Map<String, String> result = new HashMap<>();
        Map param = new HashMap();
        param.put("output", "json");
        param.put("n1", String.valueOf(currPower));//时间
        param.put("n2", String.valueOf(totalPowerConsumption));
        param.put("n3", String.valueOf(totalTime / 60000));
        param.put("n4", String.valueOf(totalPoints));
        param.put("n5", userId);
        System.err.println(param.toString());
        HttpClientResult httpClientResult = HttpClientUtils.doGet("http://10.168.1.213:9001/dischargeTest", param);
        JSONObject jsonObject = JSONObject.parseObject(httpClientResult.getContent());
        return result;
    }

    public static Map<String, String> quote(Offer offer) throws Exception {

        Map<String, String> result = new HashMap<>();
        Map param = new HashMap();
        param.put("output", "json");
        param.put("userid", offer.getUserId().toString());
        param.put("pmax", offer.getpMax().toString());
        param.put("pmin", offer.getpMin().toString());
        param.put("soc", offer.getSoc().toString());
        param.put("userrole", offer.getUserRole());
        HttpClientResult httpClientResult = HttpClientUtils.doPost("http://10.168.1.213:9001/mock/quote/", param);
        JSONObject jsonObject = JSONObject.parseObject(httpClientResult.getContent());
        return result;
    }
}
