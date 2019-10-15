package com.hhdl.controller.rest;

import com.alibaba.fastjson.JSONObject;
import com.hhdl.common.model.CommonResult;
import com.hhdl.model.TChargingStation;
import com.hhdl.model.TUser;
import com.hhdl.service.TChargingStationService;
import com.hhdl.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private TUserService tUserService;
    @Autowired
    private TChargingStationService tChargingStationService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/transfer")
    public String saveTRunLog(@RequestBody Map map) {
        try {
            String userId = (String) map.get("userId");
            String amount = (String) map.get("amount");

            String chargingStationId = (String) map.get("chargingStationId");
            TUser tUser = tUserService.selectById(userId);
            TChargingStation tChargingStation = tChargingStationService.selectById(chargingStationId);
            tUser.setAccount(String.valueOf(Double.valueOf(tUser.getAccount()) - Double.valueOf(amount)));
            tChargingStation.setAccount(String.valueOf(Double.valueOf(tChargingStation.getAccount()) + Double.valueOf(amount)));
            tUserService.insertOrUpdate(tUser);
            tChargingStationService.insertOrUpdate(tChargingStation);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/recharge")
    public CommonResult recharge(@RequestBody Map map) {
        try {
            String userId = (String) map.get("userId");
            String amount = (String) map.get("amount");

            String chargingStationId = (String) map.get("chargingStationId");
            if (userId != null) {
                TUser tUser = tUserService.selectById(userId);
                tUser.setAccount(String.valueOf(Double.valueOf(tUser.getAccount()) + Double.valueOf(amount)));
                tUserService.insertOrUpdate(tUser);
            }
            if (chargingStationId != null) {
                TChargingStation tChargingStation = tChargingStationService.selectById(chargingStationId);
                tChargingStation.setAccount(String.valueOf(Double.valueOf(tChargingStation.getAccount()) + Double.valueOf(amount)));
                tChargingStationService.insertOrUpdate(tChargingStation);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return CommonResult.success("success");
    }

    @RequestMapping("/addMount")
    public String addMount(@RequestParam String id, double value) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("value", value);
            kafkaTemplate.send("nginx_log", "key", jsonObject.toJSONString());
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }
}
