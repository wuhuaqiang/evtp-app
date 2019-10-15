package com.hhdl.entity;

import com.hhdl.endpoint.WebSocket;
import com.hhdl.model.*;
import com.hhdl.mybeanutils.MyBeanUtils;
import com.hhdl.service.EvtpElectricVehicleService;
import com.hhdl.service.EvtpLineService;
import com.hhdl.service.Impl.RedisCacheService;
import com.hhdl.util.ArithUtil;
import com.hhdl.util.NearestChargingStationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MapPointTask extends TimerTask {
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

    @Override
    public void run() {
        Map<String, Object> param = new HashMap<>();
        EvtpLine evtpLine = mapLine.getEvtpLine();
        MapPoint mapPoint = null;
        EvtpElectricVehicle evtpElectricVehicle = null;

        Queue<MapPoint> mapPoints = mapLine.getMapPoints();
        param.put("id", evtpLine.getOwerId());
        if (redisCacheService.hasKey("evtp:"+evtpLine.getOwerId())) {
            evtpElectricVehicle = (EvtpElectricVehicle) redisCacheService.get("evtp:"+evtpLine.getOwerId());
        } else {
            evtpElectricVehicle = mapLine.getEvtpElectricVehicle();
        }
        if (mapPoints.size() > 0) {
            if (evtpElectricVehicle.getCurrentPower() < 4) {
                List<Object> objects = (List)redisCacheService.lGet("evtp:evtpChargingStations", 0, -1).get(0);
                List<EvtpChargingStation> evtpChargingStations = MyBeanUtils.copyPropertiesList(objects,EvtpChargingStation.class);
                EvtpChargingStation evtpChargingStation = NearestChargingStationUtil.nearestChargingStation(evtpElectricVehicle, evtpChargingStations);
                param.put("remark", "需要充电");
                evtpElectricVehicle.setRunState("0");
                evtpElectricVehicleService.updateById(evtpElectricVehicle);
                evtpLine.setState(10);
                evtpLineService.updateById(evtpLine);
                timer.cancel();
                MapPoint evPoint = new MapPoint();
                MapPoint csPoint = new MapPoint();
                String[] splitEv = evtpElectricVehicle.getPositionVal().split(",");
                String[] splitCs = evtpChargingStation.getPositionVal().split(",");
                double latEv = Double.valueOf(splitEv[0]);
                double lngEv = Double.valueOf(splitEv[1]);
                double latCs = Double.valueOf(splitCs[0]);
                double lngCs = Double.valueOf(splitCs[1]);
                evPoint.setLng(lngEv);
                evPoint.setLat(latEv);
                csPoint.setLng(lngCs);
                csPoint.setLat(latCs);
                param.put("evPoint",evPoint);
                param.put("csPoint",csPoint);
            }else {
                evtpElectricVehicle.setRunState("0");
                mapPoint = mapPoints.remove();
                evtpElectricVehicle.setPositionVal(mapPoint.getLat() + "," + mapPoint.getLng());
                evtpElectricVehicle.setCurrentPower(ArithUtil.sub(evtpElectricVehicle.getCurrentPower(), unitPowerConsumption));
                param.put("evPoint", mapPoint);
            }
        } else {
            evtpElectricVehicle.setRunState("0");
            evtpElectricVehicleService.updateById(evtpElectricVehicle);
            evtpLine.setState(1);
            evtpLineService.updateById(evtpLine);
            timer.cancel();
        }
        try {
//            System.out.println(param);
            redisCacheService.set("evtp:"+evtpLine.getOwerId(), evtpElectricVehicle, 24 * 60 * 60);
            mapQueue.add(param);
//            webSocket.sendInfo(JSON.toJSONString(param));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
