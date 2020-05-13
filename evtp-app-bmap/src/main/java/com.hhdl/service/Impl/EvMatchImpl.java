package com.hhdl.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.model.TElectricVehicle;
import com.hhdl.service.EvMatch;
import com.hhdl.service.TElectricVehicleService;
import com.hhdl.util.Match;
import com.hhdl.util.QuotedPriceEntity;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@EnableScheduling
@Service
public class EvMatchImpl implements EvMatch {
    @Autowired
    private TElectricVehicleService tElectricVehicleService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Scheduled(cron = "0/10 * * * * ?")
    public void match() {
        Wrapper<TElectricVehicle> tElectricVehicleWrapper = new EntityWrapper<TElectricVehicle>();
        List<TElectricVehicle> list = tElectricVehicleService.selectList(tElectricVehicleWrapper);
        Match.getCurrElectricityPrice();
        for (TElectricVehicle tElectricVehicle : list) {
            Random r = new Random();
            int i = r.nextInt(100) % 2;
            Match.createBuySel(i, tElectricVehicle.getId());
//            System.out.println(tElectricVehicle);
        }
        Match.charge(0);
        List<List<QuotedPriceEntity>> buySelEntitys = Match.getBuySelEntitys();
        System.out.println(buySelEntitys);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("matchId", UUIDKey.getKey());
        jsonObject.put("context", buySelEntitys);
        kafkaTemplate.send("nginx_log", "key", jsonObject.toJSONString());
        Match.setBuySelEntitys(new ArrayList<>());
//        System.out.println(System.currentTimeMillis());
    }
}
