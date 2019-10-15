package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.model.TChargingParking;
import com.hhdl.model.TChargingStation;
import com.hhdl.service.TChargingParkingService;
import com.hhdl.service.TChargingStationService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-04-16
 */
@RestController
@RequestMapping("/tChargingParking")
public class TChargingParkingController {
    @Autowired
    private TChargingParkingService tChargingParkingService;
    @Autowired
    private TChargingStationService tChargingStationService;

    @RequestMapping("/list")
    public List<TChargingParking> getPage() {
        Wrapper<TChargingParking> tChargingParkingWrapper = new EntityWrapper<TChargingParking>();
        return tChargingParkingService.selectList(tChargingParkingWrapper);
    }

    @RequestMapping("/save")
    public String saveLine(@RequestBody TChargingParking tChargingParking) {
        try {
            if (tChargingParking.getId() == null) {
                tChargingParking.setId(UUIDKey.getKey());
            }
            tChargingParkingService.insertOrUpdate(tChargingParking);
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
            TChargingParking tChargingParking = new TChargingParking();
            for (TChargingStation tChargingStation : tChargingStations) {
                tChargingParking.setId(UUIDKey.getKey());
                tChargingParking.setCsId(tChargingStation.getId());
                tChargingParking.setNumber(5d);
                String name = tChargingStation.getName() + "停车场";
                System.out.println(name);
                tChargingParking.setName(name);
                tChargingParking.setState("1");
                tChargingParkingService.insertOrUpdate(tChargingParking);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }
}

