package com.hhdl.service.Impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.common.model.Account;
import com.hhdl.common.util.DateTimeUtils;
import com.hhdl.common.util.JsonUtils;
import com.hhdl.model.*;
import com.hhdl.service.*;
import com.hhdl.util.BlockChainUtils;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EvtpInitMapServiceImpl implements EvtpInitMapService {
    @Autowired
    private EvtpSimulationParametersService evtpSimulationParametersService;
    @Autowired
    private EvtpElectricVehicleService evtpElectricVehicleService;
    @Autowired
    private EvtpChargingParkingService evtpChargingParkingService;
    @Autowired
    private EvtpChargingPileService evtpChargingPileService;
    @Autowired
    private EvtpChargingStationService evtpChargingStationService;
    @Autowired
    private EvtpPointsService evtpPointsService;
    @Autowired
    private EvtpUserService evtpUserService;
    @Autowired
    private EvtpLinePointsService evtpLinePointsService;
    @Autowired
    private EvtpLineService evtpLineService;
    @Autowired
    private RedisCacheService redisCacheService;


    @Override
    public int initMapParameter() throws Exception {
        evtpUserService.delAll(); //删除所有用户
        evtpElectricVehicleService.delAll(); //删除所有电动汽车
        evtpChargingStationService.delAll(); //删除所有充电站
        evtpChargingPileService.delAll(); //删除充电桩
        evtpChargingParkingService.delAll(); //删除停车场
        evtpLinePointsService.delAll(); //删除线路点位
        evtpLineService.delAll(); //删除所有线路
        String sessionId = (String) redisCacheService.get("sessionId");

        Wrapper<EvtpSimulationParameters> evtpSimulationParametersWrapper = new EntityWrapper<EvtpSimulationParameters>();
        List<EvtpSimulationParameters> evtpSimulationParametersList = evtpSimulationParametersService.selectList(evtpSimulationParametersWrapper);
        EvtpSimulationParameters evtpSimulationParameters = evtpSimulationParametersList.get(0);
        if (evtpSimulationParameters == null) {
            return 100;
        }
        Wrapper<EvtpPoints> evtpPointsWrapper = new EntityWrapper<EvtpPoints>();
        evtpPointsWrapper.where("remark={0}", "家");
        List<EvtpPoints> evtpPoints_home = evtpPointsService.selectList(evtpPointsWrapper);
        evtpPointsWrapper = new EntityWrapper<EvtpPoints>();
        evtpPointsWrapper.where("remark={0}", "公司");
        List<EvtpPoints> evtpPoints_company = evtpPointsService.selectList(evtpPointsWrapper);
        evtpPointsWrapper = new EntityWrapper<EvtpPoints>();
        evtpPointsWrapper.where("remark={0}", "充电站");
        List<EvtpPoints> evtpPoints_chargingStation = evtpPointsService.selectList(evtpPointsWrapper);
        evtpPointsWrapper = new EntityWrapper<EvtpPoints>();
        List<String> params = new ArrayList<>();
        params.add("充电站");
        params.add("公司");
        params.add("家");
        evtpPointsWrapper.notIn("remark", params);
        List<EvtpPoints> evtpPoints_other = evtpPointsService.selectList(evtpPointsWrapper);
        Integer electricVehicleNumber = evtpSimulationParameters.getElectricVehicleNumber();
        Integer chargingStationNumber = evtpSimulationParameters.getChargingStationNumber();
        Integer chargingPileNumber = evtpSimulationParameters.getChargingPileNumber();
        Integer parkingSpaceNumber = evtpSimulationParameters.getParkingSpaceNumber();
        Random r = new Random();
        for (int i = 0; i < electricVehicleNumber; i++) {
            int homeMark = r.nextInt(evtpPoints_home.size()-1);
            int companyMark = r.nextInt(evtpPoints_company.size()-1);
            EvtpUser evtpUser = new EvtpUser();
            EvtpElectricVehicle evtpElectricVehicle = new EvtpElectricVehicle();
            evtpElectricVehicle.setId(UUIDKey.getKey());
            evtpElectricVehicle.setRunState("0");
            evtpElectricVehicle.setCurrentPower(Double.valueOf(evtpSimulationParameters.getBatteryCapacity()));
            evtpElectricVehicle.setBatteryCapacity(Double.valueOf(evtpSimulationParameters.getBatteryCapacity()));
            evtpElectricVehicle.setChargingEfficiency(evtpSimulationParameters.getChargingEfficiency());
            evtpElectricVehicle.setChargingSpeed(evtpSimulationParameters.getChargingSpeed());
            evtpElectricVehicle.setPosition(evtpPoints_home.get(homeMark).getAddress());
            evtpElectricVehicle.setPositionVal(evtpPoints_home.get(homeMark).getLat() + "," + evtpPoints_home.get(homeMark).getLng());
            evtpUser.setCompanyPositionId(evtpPoints_company.get(companyMark).getId());
            evtpUser.setHomePositionId(evtpPoints_home.get(homeMark).getId());
            evtpUser.setAddress(evtpPoints_home.get(homeMark).getAddress());
            evtpUser.setName("user"+(i+1));
            String otherId = evtpPoints_other.get(r.nextInt(evtpPoints_other.size() - 1)).getId() + "," + evtpPoints_other.get(r.nextInt(evtpPoints_other.size() - 1)).getId() + "," + evtpPoints_other.get(r.nextInt(evtpPoints_other.size() - 1)).getId();
            evtpUser.setOtherPositionId(otherId);
            evtpUser.setId(UUIDKey.getKey());
            evtpUser.setEvId(evtpElectricVehicle.getId());
            evtpUser.setCreatedate(UUIDKey.getDate());
            evtpUser.setAccount("200.00");
            List<String> args = new ArrayList<>();
//            args.add(evtpUser.getId()+"Account");
//            args.add(evtpUser.getAccount());
            String fcnName = "initAccount";
            Account account = new Account();
            account.setRealName(evtpUser.getName());
            account.setAccountNo(evtpUser.getId()+"Account");
            account.setAccountBalance(Double.valueOf(evtpUser.getAccount()));
            account.setCreatedTime(DateTimeUtils.formatNow());
            args.add(JsonUtils.object2Json(account));
            BlockChainUtils.executeBlockChain(args, fcnName, sessionId,"invoke");
            evtpUserService.insert(evtpUser);
            evtpElectricVehicleService.insert(evtpElectricVehicle);
        }
        for (int i = 0; i < chargingStationNumber; i++) {
            int chargingStationMark = r.nextInt(evtpPoints_chargingStation.size()-1);
            EvtpPoints evtpPoints = evtpPoints_chargingStation.get(chargingStationMark);
            EvtpChargingStation evtpChargingStation = new EvtpChargingStation();
            evtpChargingStation.setId(UUIDKey.getKey());
            evtpChargingStation.setPileNumber(evtpSimulationParameters.getChargingPileNumber());
            evtpChargingStation.setChargingEfficiency(Float.valueOf(evtpSimulationParameters.getChargingEfficiency()));
            evtpChargingStation.setPosition(evtpPoints.getAddress());
            evtpChargingStation.setPositionVal(evtpPoints.getLat() + "," + evtpPoints.getLng());
            evtpChargingStation.setName(evtpPoints.getTitle());
            evtpChargingStation.setCreateTime(new Date());
            evtpChargingStation.setState("1");
            evtpChargingStation.setAccount("500.00");
            List<String> args = new ArrayList<>();
//            args.add(evtpChargingStation.getId()+"Account");
//            args.add(evtpChargingStation.getAccount());
            Account account = new Account();
            account.setRealName(evtpChargingStation.getName());
            account.setAccountNo(evtpChargingStation.getId()+"Account");
            account.setAccountBalance(Double.valueOf(evtpChargingStation.getAccount()));
            account.setCreatedTime(DateTimeUtils.formatNow());
            args.add(JsonUtils.object2Json(account));
            String fcnName = "initAccount";
            BlockChainUtils.executeBlockChain(args, fcnName, sessionId,"invoke");
            evtpChargingStationService.insert(evtpChargingStation);
        }
        return 200;
    }
}
