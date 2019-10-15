package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.model.TElectricVehicle;
import com.hhdl.model.TPowerHistory;
import com.hhdl.service.TElectricVehicleService;
import com.hhdl.service.TPowerHistoryService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-01-21
 */
@RestController
@RequestMapping("/tPowerHistory")
public class TPowerHistoryController {
    @Autowired
    private TPowerHistoryService tPowerHistoryService;
    @Autowired
    private TElectricVehicleService tElectricVehicleService;

    @RequestMapping("/list")
    public List<TPowerHistory> getList() {
        Wrapper<TPowerHistory> tPowerHistoryWrapper = new EntityWrapper<TPowerHistory>();
        return tPowerHistoryService.selectList(tPowerHistoryWrapper);
    }

    @RequestMapping("/echarts")
    public Map getEchartsData() {
        Wrapper<TElectricVehicle> tElectricVehicleWrapper = new EntityWrapper<TElectricVehicle>();
        List<Map> mapList = tElectricVehicleService.selectListWithUserId();
        Map result = new HashMap();
        List timeList = new ArrayList();
        List userNameList = new ArrayList();
        Map powerMap = new HashMap();
        boolean flog = true;
        for (Map map : mapList) {
            Wrapper<TPowerHistory> tPowerHistoryWrapper = new EntityWrapper<TPowerHistory>();
            tPowerHistoryWrapper.where("ower_id={0}", map.get("userId").toString()).orderBy("time", true);
            List<TPowerHistory> tPowerHistories = tPowerHistoryService.selectList(tPowerHistoryWrapper);
            List powerList = new ArrayList();
            for (TPowerHistory tPowerHistory : tPowerHistories) {
                if (flog) {
                    timeList.add(tPowerHistory.getTime());
                }
                powerList.add(tPowerHistory.getPower());
            }
            powerMap.put(map.get("userName").toString(), powerList);
            userNameList.add(map.get("userName").toString());
            flog = false;
        }
        result.put("timeList", timeList);
        result.put("powerMap", powerMap);
        result.put("userNameList", userNameList);
        return result;
    }

    @RequestMapping("/save")
    public String savePowerHistory(@RequestBody TPowerHistory tPowerHistory) {


        try {
            List<Map> mapList = tElectricVehicleService.selectListWithUserId();
            for (Map map : mapList) {
                TPowerHistory his = new TPowerHistory();
                his.setId(UUIDKey.getKey());
                his.setOwerId(map.get("userId").toString());
                his.setEvId(map.get("id").toString());
                his.setPower(Double.valueOf(map.get("power").toString()));
                his.setTime(tPowerHistory.getTime());
                his.setRemark(tPowerHistory.getTime() + "时刻的电量为" + map.get("batteryCapacity").toString());
                tPowerHistoryService.insertOrUpdate(his);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/update")
    public String updateUser(@RequestBody TPowerHistory tPowerHistory) {
        try {
            tPowerHistoryService.insertOrUpdate(tPowerHistory);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/delbyId")
    public String delbyId(@RequestBody String id) {
        try {
            tPowerHistoryService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "success";
    }
}

