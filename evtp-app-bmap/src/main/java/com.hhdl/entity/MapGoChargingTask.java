package com.hhdl.entity;

import com.alibaba.fastjson.JSONObject;
import com.hhdl.common.model.ElectricityTradingRecord;
import com.hhdl.common.model.Offer;
import com.hhdl.common.util.JsonUtils;
import com.hhdl.endpoint.WebSocket;
import com.hhdl.model.*;
import com.hhdl.mybeanutils.MyBeanUtils;
import com.hhdl.service.EvtpElectricVehicleService;
import com.hhdl.service.EvtpLineService;
import com.hhdl.service.EvtpTransactionService;
import com.hhdl.service.Impl.RedisCacheService;
import com.hhdl.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MapGoChargingTask extends TimerTask {
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private RedisCacheService redisCacheService;
    private MapLine mapLine;
    private Queue<Map> mapQueue;
    private Timer timer;
    private double distance;
    @Autowired
    private EvtpElectricVehicleService evtpElectricVehicleService;
    @Autowired
    private EvtpLineService evtpLineService;
    @Autowired
    private MapContinueTask mapContinueTask;
    @Autowired
    private EvtpTransactionService evtpTransactionService;
    /**
     * 单位耗电量
     */
    private double unitPowerConsumption;


    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public MapLine getMapLine() {
        return mapLine;
    }

    public void setMapLine(MapLine mapLine) {
        this.mapLine = mapLine;
    }

    public Queue<Map> getMapQueue() {
        return mapQueue;
    }

    public void setMapQueue(Queue<Map> mapQueue) {
        this.mapQueue = mapQueue;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getUnitPowerConsumption() {
        return unitPowerConsumption;
    }

    public void setUnitPowerConsumption(double unitPowerConsumption) {
        this.unitPowerConsumption = unitPowerConsumption;
    }

    public RedisCacheService getRedisCacheService() {
        return redisCacheService;
    }

    public void setRedisCacheService(RedisCacheService redisCacheService) {
        this.redisCacheService = redisCacheService;
    }

    public EvtpElectricVehicleService getEvtpElectricVehicleService() {
        return evtpElectricVehicleService;
    }

    public void setEvtpElectricVehicleService(EvtpElectricVehicleService evtpElectricVehicleService) {
        this.evtpElectricVehicleService = evtpElectricVehicleService;
    }

    public EvtpLineService getEvtpLineService() {
        return evtpLineService;
    }

    public void setEvtpLineService(EvtpLineService evtpLineService) {
        this.evtpLineService = evtpLineService;
    }

    public MapContinueTask getMapContinueTask() {
        return mapContinueTask;
    }

    public void setMapContinueTask(MapContinueTask mapContinueTask) {
        this.mapContinueTask = mapContinueTask;
    }

    public EvtpTransactionService getEvtpTransactionService() {
        return evtpTransactionService;
    }

    public void setEvtpTransactionService(EvtpTransactionService evtpTransactionService) {
        this.evtpTransactionService = evtpTransactionService;
    }

    @Override
    public void run() {
        Map<String, Object> param = new HashMap<>();
        EvtpLine evtpLine = mapLine.getEvtpLine();
        MapPoint mapPoint = null;
        EvtpElectricVehicle evtpElectricVehicle = null;

        Queue<MapPoint> mapPoints = mapLine.getMapPoints();
        param.put("id", evtpLine.getOwerId());
        if (redisCacheService.hasKey("evtp:" + evtpLine.getOwerId())) {
            evtpElectricVehicle = (EvtpElectricVehicle) redisCacheService.get("evtp:" + evtpLine.getOwerId());
        } else {
            evtpElectricVehicle = mapLine.getEvtpElectricVehicle();
        }
        if (mapPoints.size() > 0) {
            if (evtpElectricVehicle.getCurrentPower() < 0) {
                param.put("remark", "需要救援");
                evtpElectricVehicle.setRunState("0");
                evtpElectricVehicleService.updateById(evtpElectricVehicle);
                evtpLine.setState(20);
                evtpLineService.updateById(evtpLine);
                timer.cancel();
            } else {
                evtpElectricVehicle.setRunState("1");
                mapPoint = mapPoints.remove();
                evtpElectricVehicle.setPositionVal(mapPoint.getLat() + "," + mapPoint.getLng());
                evtpElectricVehicle.setCurrentPower(ArithUtil.sub(evtpElectricVehicle.getCurrentPower(), unitPowerConsumption));
                param.put("evPoint", mapPoint);
                param.put("mark", "去充电");
            }
        } else {
//            evtpElectricVehicle.setRunState("0");
            try {
                evtpElectricVehicle.setRunState("0");
                String currentPower = String.valueOf(evtpElectricVehicle.getCurrentPower());
                Map<String, String> chargingInfo = ElectricVehiclePowerUtil.getChargingInfo(evtpElectricVehicle.getCurrentPower(), evtpElectricVehicle.getBatteryCapacity(), 5.0, evtpLine.getOwerId());
                Integer chargeTime = Integer.valueOf(chargingInfo.get("chargeTime"));
                Double endPower = Double.valueOf(chargingInfo.get("endPower"));
                evtpElectricVehicle.setCurrentPower(endPower);
                evtpElectricVehicleService.updateById(evtpElectricVehicle);
                List<Object> objects = (List) redisCacheService.lGet("evtp:evtpChargingStations", 0, -1).get(0);
                List<EvtpChargingStation> evtpChargingStations = MyBeanUtils.copyPropertiesList(objects, EvtpChargingStation.class);
                EvtpChargingStation evtpChargingStation = NearestChargingStationUtil.nearestChargingStation(evtpElectricVehicle, evtpChargingStations);
                String sessionId = (String) redisCacheService.get("sessionId");
//                Map<String, String> params = new HashMap<>();
//                params.put("args", "[\"" + currentPower + "\",\"" + chargingInfo.get("realSOCChange") +
//                        "\",\"" + chargingInfo.get("finalPay") + "\",\"" + evtpLine.getOwerId() + "\",\"" +
//                        evtpChargingStation.getId() + "\",\"" + chargingInfo.get("startTime") + "\",\"" + chargingInfo.get("chargeTime") + "\"]");
//                params.put("fcn", "addElectricityTradingRecord");
//                BlockChainUtils.queryBlockChainInfo("chaincode/invoke", params, sessionId);
                evtpTransactionService.transferAccounts(evtpLine.getOwerId(), evtpChargingStation.getId(), chargingInfo.get("finalPay"));
                List<String> args = new ArrayList<>();
                ElectricityTradingRecord tradingRecord = new ElectricityTradingRecord();
                tradingRecord.setStartTime(chargingInfo.get("startTime"));
                tradingRecord.setAddSoc(chargingInfo.get("realSOCChange"));
                tradingRecord.setBuyerId(evtpLine.getOwerId());
                tradingRecord.setChargingTime(chargingInfo.get("chargeTime"));
                tradingRecord.setMoney(chargingInfo.get("finalPay"));
                tradingRecord.setOldSoc(currentPower);
                tradingRecord.setSellerId(evtpChargingStation.getId());
//                args.add(currentPower);
//                args.add(chargingInfo.get("realSOCChange"));
//                args.add(chargingInfo.get("finalPay"));
//                args.add(evtpLine.getOwerId());
//                args.add(evtpChargingStation.getId());
//                args.add(chargingInfo.get("startTime"));
//                args.add(chargingInfo.get("chargeTime"));
                Offer offer = new Offer();
                offer.setUserId(evtpLine.getOwerId());
                offer.setUserRole("charge");
                offer.setSoc(Double.valueOf(chargingInfo.get("realSOCChange")));
                offer.setpMax(Match.getRandomNumber(0.5,0.7));
                offer.setpMin(Match.getRandomNumber(0.2,0.3));
                try {
                    ElectricVehiclePowerUtil.quote(offer);
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
                args.add(JsonUtils.object2Json(tradingRecord));
                String fcnName = "addElectricityTradingRecord";

                BlockChainUtils.executeBlockChain(args, fcnName, sessionId, "invoke");
                args.clear();
                args.add(evtpLine.getOwerId() + "Account");
                args.add(evtpChargingStation.getId() + "Account");
                args.add(chargingInfo.get("finalPay"));
                fcnName = "transferAccounts";
                BlockChainUtils.executeBlockChain(args, fcnName, sessionId, "invoke");
                evtpLine.setState(1);
                evtpLineService.updateById(evtpLine);
                Timer timer1 = new Timer();
                mapContinueTask = new MapContinueTask();
                mapContinueTask.setEvtpLineService(evtpLineService);
                mapContinueTask.setMapLine(mapLine);
                mapContinueTask.setEvtpElectricVehicleService(evtpElectricVehicleService);
                mapContinueTask.setRedisCacheService(redisCacheService);
                mapContinueTask.setTimer(timer1);
                mapContinueTask.setWebSocket(webSocket);
                mapContinueTask.setMapQueue(mapQueue);
                chargeTime = 15000;
                timer1.schedule(mapContinueTask, chargeTime);
                param.put("remark", "正在充电");
            } catch (Exception e) {
                e.printStackTrace();
            }
            timer.cancel();
        }
        try {
            System.out.println(param);
            redisCacheService.set("evtp:" + evtpLine.getOwerId(), evtpElectricVehicle, 24 * 60 * 60);
            mapQueue.add(param);
//            webSocket.sendInfo(JSON.toJSONString(param));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
