package com.hhdl.entity;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.endpoint.WebSocket;
import com.hhdl.model.EvtpElectricVehicle;
import com.hhdl.model.EvtpLine;
import com.hhdl.model.MapLine;
import com.hhdl.model.MapPoint;
import com.hhdl.service.EvtpElectricVehicleService;
import com.hhdl.service.EvtpLineService;
import com.hhdl.service.Impl.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MapContinueTask extends TimerTask {
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
        if (redisCacheService.hasKey("evtp:" + evtpLine.getOwerId())) {
            evtpElectricVehicle = (EvtpElectricVehicle) redisCacheService.get("evtp:" + evtpLine.getOwerId());
        } else {
            evtpElectricVehicle = mapLine.getEvtpElectricVehicle();
        }
        Wrapper<EvtpLine> evtpLineWrapper = new EntityWrapper<>();
        evtpLineWrapper.where("ower_id={0}", evtpLine.getOwerId()).where("state={0}", 10);
        List<EvtpLine> evtpLines = evtpLineService.selectList(evtpLineWrapper);
        System.out.println(evtpLines);
        EvtpLine evtpLine1 = evtpLines.get(0);
        String remark = evtpLine1.getRemark();
        MapPoint evPoint = new MapPoint();
        MapPoint edPoint = new MapPoint();
        String[] splitEv = evtpElectricVehicle.getPositionVal().split(",");
        String[] endPointval = evtpLine1.getEndPointVal().split(",");
        double latEv = Double.valueOf(splitEv[0]);
        double lngEv = Double.valueOf(splitEv[1]);
        double latEd = Double.valueOf(endPointval[0]);
        double lngEd = Double.valueOf(endPointval[1]);
        evPoint.setLng(lngEv);
        evPoint.setLat(latEv);
        edPoint.setLng(lngEd);
        edPoint.setLat(latEd);
        evtpElectricVehicle.setRunState("0");
        evtpElectricVehicleService.updateById(evtpElectricVehicle);
        param.put("evPoint", evPoint);
        param.put("edPoint", edPoint);
        param.put("remark", "继续"+remark);
        mapQueue.add(param);
    }
}
