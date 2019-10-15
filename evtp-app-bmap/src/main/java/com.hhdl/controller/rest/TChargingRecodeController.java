package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.model.TChargingRecode;
import com.hhdl.model.TChargingStation;
import com.hhdl.model.TElectricVehicle;
import com.hhdl.service.TChargingRecodeService;
import com.hhdl.service.TChargingStationService;
import com.hhdl.service.TElectricVehicleService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-04-16
 */
@RestController
@RequestMapping("/tChargingRecode")
public class TChargingRecodeController {
    @Autowired
    private TChargingRecodeService tChargingRecodeService;
    @Autowired
    private TChargingStationService tChargingStationService;
    @Autowired
    private TElectricVehicleService tElectricVehicleService;

    @RequestMapping("/list")
    public List<TChargingRecode> getPage() {
        Wrapper<TChargingRecode> tChargingRecodeWrapper = new EntityWrapper<TChargingRecode>();
        return tChargingRecodeService.selectList(tChargingRecodeWrapper);
    }

    @RequestMapping("/save")
    public String saveTChargingRecode(@RequestBody TChargingRecode tChargingRecode) {
        try {
            if (tChargingRecode.getId() == null) {
                tChargingRecode.setId(UUIDKey.getKey());
            }
            tChargingRecodeService.insertOrUpdate(tChargingRecode);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/update")
    public String updateTChargingRecode(@RequestBody String evId) {
        try {
            Wrapper<TChargingRecode> tChargingRecodeWrapper = new EntityWrapper<TChargingRecode>();
            tChargingRecodeWrapper.where("ev_id={0}", evId).where("state={0}", "1");
            TChargingRecode tChargingRecode = tChargingRecodeService.selectOne(tChargingRecodeWrapper);
            tChargingRecode.setState("0");
            tChargingRecodeService.insertOrUpdate(tChargingRecode);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/bathInsert")
    public String saveAll() {
        try {
            Wrapper<TChargingStation> TChargingStationWrapper = new EntityWrapper<TChargingStation>();
            List<TChargingStation> tChargingStations = tChargingStationService.selectList(TChargingStationWrapper);
            Wrapper<TElectricVehicle> tElectricVehicleEntityWrapper = new EntityWrapper<TElectricVehicle>();
            List<TElectricVehicle> tElectricVehicles = tElectricVehicleService.selectList(tElectricVehicleEntityWrapper);

            for (TElectricVehicle tElectricVehicle : tElectricVehicles) {
                int i = (int) (1 + Math.random() * 10);
                String csId = null;
                if (i < tChargingStations.size()) {
                    csId = tChargingStations.get(i).getId();
                } else {
                    csId = tChargingStations.get(0).getId();
                }
                TChargingRecode tChargingRecode = new TChargingRecode();
                tChargingRecode.setCsId(csId);
                tChargingRecode.setId(UUIDKey.getKey());
                tChargingRecode.setEvId(tElectricVehicle.getId());
                tChargingRecode.setState("1");
                tChargingRecodeService.insertOrUpdate(tChargingRecode);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }
}

