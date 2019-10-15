package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.common.model.CommonResult;
import com.hhdl.model.*;
import com.hhdl.mybeanutils.MyBeanUtils;
import com.hhdl.service.*;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2018-12-03
 */
@RestController
@RequestMapping("/tChargingStation")
public class TChargingStationController {
    @Autowired
    private TChargingStationService tChargingStationService;
    @Autowired
    private TChargingPileService tChargingPileService;
    @Autowired
    private TChargingParkingService tChargingParkingService;
    @Autowired
    private TChargingRecodeService tChargingRecodeService;
    @Autowired
    private TUserService tUserService;

    @RequestMapping("/list")
    public CommonResult getPage() {
        Wrapper<TChargingStation> TChargingStationWrapper = new EntityWrapper<TChargingStation>();
        return CommonResult.success(tChargingStationService.selectList(TChargingStationWrapper));
    }

    @RequestMapping("/save")
    public String saveLine(@RequestBody TChargingStation TChargingStation) {
        try {
            if (TChargingStation.getId() == null) {
                TChargingStation.setId(UUIDKey.getKey());
                TChargingStation.setCreateTime(new Date());
            }
            tChargingStationService.insertOrUpdate(TChargingStation);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/delbyId")
    public String delbyId(@RequestBody String id) {
        try {
            tChargingStationService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/getChargingStationByName")
    public List<TChargingStationwithCp> getChargingStationByName(@RequestBody String name) {
        List<TChargingStationwithCp> list = new ArrayList<TChargingStationwithCp>();
        Wrapper<TChargingStation> tChargingStationWrapper = new EntityWrapper<TChargingStation>();
        Wrapper<TChargingPile> tChargingPileWrapper = new EntityWrapper<TChargingPile>();
        Wrapper<TChargingParking> tChargingParkingWrapper = new EntityWrapper<TChargingParking>();
        Wrapper<TChargingRecode> tChargingRecodeWrapper = new EntityWrapper<TChargingRecode>();
        tChargingStationWrapper.where("name={0}", name);
        List<TChargingStation> tChargingStations = tChargingStationService.selectList(tChargingStationWrapper);
        for (TChargingStation tChargingStation : tChargingStations) {
            tChargingPileWrapper.where("cs_id={0}", tChargingStation.getId()).orderBy("name", true);
            List<TChargingPile> tChargingPiles = tChargingPileService.selectList(tChargingPileWrapper);
            tChargingParkingWrapper.where("cs_id={0}", tChargingStation.getId());
            List<TChargingParking> tChargingParkings = tChargingParkingService.selectList(tChargingParkingWrapper);
            tChargingRecodeWrapper.where("cs_id={0}", tChargingStation.getId()).where("state={0}", "1");
            List<TChargingRecode> tChargingRecodes = tChargingRecodeService.selectList(tChargingRecodeWrapper);
            List<TChargingRecodeWithName> tChargingRecodeWithNames =new ArrayList<TChargingRecodeWithName>();
            for (TChargingRecode tChargingRecode:tChargingRecodes){
                Wrapper<TUser> tUserWrapper = new EntityWrapper<TUser>();
                tUserWrapper.where("ev_id={0}", tChargingRecode.getEvId());
                TUser tUser = tUserService.selectOne(tUserWrapper);
                TChargingRecodeWithName tChargingRecodeWithName = new TChargingRecodeWithName();
                MyBeanUtils.copyProperties(tChargingRecode, tChargingRecodeWithName);
                tChargingRecodeWithName.setName(tUser.getName());
                tChargingRecodeWithNames.add(tChargingRecodeWithName);
            }
            TChargingStationwithCp stationwithCp = new TChargingStationwithCp();
            MyBeanUtils.copyProperties(tChargingStation, stationwithCp);
            stationwithCp.settChargingPiles(tChargingPiles);
            stationwithCp.settChargingParkings(tChargingParkings);
            stationwithCp.settChargingRecodes(tChargingRecodes);
            stationwithCp.settChargingRecodeWithNames(tChargingRecodeWithNames);
            list.add(stationwithCp);
        }
        return list;

    }
}

