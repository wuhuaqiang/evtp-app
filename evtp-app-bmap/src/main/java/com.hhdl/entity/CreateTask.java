package com.hhdl.entity;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.endpoint.WebSocket;
import com.hhdl.model.*;
import com.hhdl.mybeanutils.MyBeanUtils;
import com.hhdl.service.EvtpElectricVehicleService;
import com.hhdl.service.EvtpLineService;
import com.hhdl.service.EvtpPointsService;
import com.hhdl.service.EvtpUserService;
import com.hhdl.service.Impl.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CreateTask extends TimerTask {
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private RedisCacheService redisCacheService;
    private MapLine mapLine;
    private Queue<Map> mapQueue;
    private Timer timer;
    private Integer wheel = 0;
    @Autowired
    private EvtpElectricVehicleService evtpElectricVehicleService;
    @Autowired
    private EvtpLineService evtpLineService;
    @Autowired
    private EvtpUserService evtpUserService;
    @Autowired
    private EvtpPointsService evtpPointsService;

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public RedisCacheService getRedisCacheService() {
        return redisCacheService;
    }

    public void setRedisCacheService(RedisCacheService redisCacheService) {
        this.redisCacheService = redisCacheService;
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

    public Integer getWheel() {
        return wheel;
    }

    public void setWheel(Integer wheel) {
        this.wheel = wheel;
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

    public EvtpUserService getEvtpUserService() {
        return evtpUserService;
    }

    public void setEvtpUserService(EvtpUserService evtpUserService) {
        this.evtpUserService = evtpUserService;
    }

    public EvtpPointsService getEvtpPointsService() {
        return evtpPointsService;
    }

    public void setEvtpPointsService(EvtpPointsService evtpPointsService) {
        this.evtpPointsService = evtpPointsService;
    }

    @Override
    public void run() {
        this.wheel++;
        if (this.wheel < 6) {
            List<EvtpUser> evtpUsers = null;
            Random r = new Random();
            List<EvtpPoints> evtpPoints_other = null;
            List<EvtpElectricVehicle> evtpElectricVehicles = new ArrayList<>();
            if (redisCacheService.hasKey("evtp:evtpUsers")) {
                List<Object> objects = (List) redisCacheService.lGet("evtp:evtpUsers", 0, -1).get(0);
                evtpUsers = MyBeanUtils.copyPropertiesList(objects, EvtpUser.class);
            } else {
                Wrapper<EvtpUser> evtpUserWrapper = new EntityWrapper<>();
                evtpUsers = evtpUserService.selectList(evtpUserWrapper);
                redisCacheService.lSet("evtp:evtpUsers", evtpUsers);
            }
            for (EvtpUser evtpUser : evtpUsers) {
                EvtpElectricVehicle evtpElectricVehicle = (EvtpElectricVehicle) redisCacheService.get("evtp:" + evtpUser.getId());
                if ("0".equals(evtpElectricVehicle.getRunState()) && evtpElectricVehicle.getCurrentPower() > 4) {
                    evtpElectricVehicle.setRemark(evtpUser.getId());
                    evtpElectricVehicles.add(evtpElectricVehicle);
                }
            }
            if (evtpElectricVehicles.size() > 0) {
                Wrapper<EvtpPoints> evtpPointsWrapper = new EntityWrapper<EvtpPoints>();
                List<String> params = new ArrayList<>();
                params.add("充电站");
                params.add("公司");
//            params.add("家");
                evtpPointsWrapper.notIn("remark", params);
                evtpPoints_other = evtpPointsService.selectList(evtpPointsWrapper);
            }
            for (EvtpElectricVehicle evtpElectricVehicle : evtpElectricVehicles) {
                Map<String, Object> param = new HashMap<>();
                param.put("id", evtpElectricVehicle.getRemark());
                MapPoint evPoint = new MapPoint();
                MapPoint edPoint = new MapPoint();
                String[] splitEv = evtpElectricVehicle.getPositionVal().split(",");
                EvtpPoints endPointval = evtpPoints_other.get(r.nextInt(evtpPoints_other.size()-1));
                double latEv = Double.valueOf(splitEv[0]);
                double lngEv = Double.valueOf(splitEv[1]);
                double latEd = Double.valueOf(endPointval.getLat());
                double lngEd = Double.valueOf(endPointval.getLng());
                evPoint.setLng(lngEv);
                evPoint.setLat(latEv);
                edPoint.setLng(lngEd);
                edPoint.setLat(latEd);
                param.put("evPoint", evPoint);
                param.put("edPoint", edPoint);
                param.put("remark", endPointval.getRemark());
                mapQueue.add(param);
            }
        }

    }
}
