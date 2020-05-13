package com.hhdl.entity;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.endpoint.WebSocket;
import com.hhdl.model.*;
import com.hhdl.service.*;
import com.hhdl.service.Impl.RedisCacheService;
import com.hhdl.util.ArithUtil;
import com.hhdl.util.ElectricVehiclePowerUtil;
import com.hhdl.util.PointsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

@Component
public class CheckCharging extends TimerTask {
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private EvtpElectricVehicleService evtpElectricVehicleService;
    @Autowired
    private EvtpLineService evtpLineService;
    @Autowired
    private EvtpSimulationParametersService evtpSimulationParametersService;
    @Autowired
    private MapGoChargingTask mapGoChargingTask;
    @Autowired
    private WebPointsTask webPointsTask;
    @Autowired
    private EvtpLinePointsService evtpLinePointsService;
    @Autowired
    private EvtpUserService evtpUserService;
    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private EvtpTransactionService evtpTransactionService;

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
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

    public EvtpSimulationParametersService getEvtpSimulationParametersService() {
        return evtpSimulationParametersService;
    }

    public void setEvtpSimulationParametersService(EvtpSimulationParametersService evtpSimulationParametersService) {
        this.evtpSimulationParametersService = evtpSimulationParametersService;
    }

    public MapGoChargingTask getMapGoChargingTask() {
        return mapGoChargingTask;
    }

    public void setMapGoChargingTask(MapGoChargingTask mapGoChargingTask) {
        this.mapGoChargingTask = mapGoChargingTask;
    }

    public WebPointsTask getWebPointsTask() {
        return webPointsTask;
    }

    public void setWebPointsTask(WebPointsTask webPointsTask) {
        this.webPointsTask = webPointsTask;
    }

    public EvtpLinePointsService getEvtpLinePointsService() {
        return evtpLinePointsService;
    }

    public void setEvtpLinePointsService(EvtpLinePointsService evtpLinePointsService) {
        this.evtpLinePointsService = evtpLinePointsService;
    }

    public EvtpUserService getEvtpUserService() {
        return evtpUserService;
    }

    public void setEvtpUserService(EvtpUserService evtpUserService) {
        this.evtpUserService = evtpUserService;
    }

    public RedisCacheService getRedisCacheService() {
        return redisCacheService;
    }

    public void setRedisCacheService(RedisCacheService redisCacheService) {
        this.redisCacheService = redisCacheService;
    }

    public EvtpTransactionService getEvtpTransactionService() {
        return evtpTransactionService;
    }

    public void setEvtpTransactionService(EvtpTransactionService evtpTransactionService) {
        this.evtpTransactionService = evtpTransactionService;
    }

    @Override
    public void run() {
        Wrapper<EvtpLine> evtpLineWrapper = new EntityWrapper<EvtpLine>();
        evtpLineWrapper.where("name={0}", "充电").where("state={0}", 0);
        List<EvtpLine> evtpLines = evtpLineService.selectList(evtpLineWrapper);
        Wrapper<EvtpSimulationParameters> parametersWrapper = new EntityWrapper<>();
        EvtpSimulationParameters parameters = null;
        if(redisCacheService.hasKey("evtp:evtpSimulationParameter")){
            parameters = (EvtpSimulationParameters)redisCacheService.get("evtp:evtpSimulationParameter");
        }else {
            List<EvtpSimulationParameters> evtpSimulationParameters = evtpSimulationParametersService.selectList(parametersWrapper);
            parameters = evtpSimulationParameters.get(0);
        }
        double powerConsumptionPerKilometer = Double.valueOf(parameters.getPowerConsumptionPerKilometer());
        for (EvtpLine evtpLine : evtpLines) {
            evtpLine.setState(50);
            evtpLineService.updateById(evtpLine);
            MapLine mapLine = new MapLine();
            Queue<MapPoint> mapPointQueue = new LinkedBlockingDeque<>();
            List<MapPoint> mapPoints = new ArrayList<>();
            Wrapper<EvtpLinePoints> evtpLinePointsWrapper = new EntityWrapper<EvtpLinePoints>();
            evtpLinePointsWrapper.where("line_id={0}", evtpLine.getId()).orderBy("sort", true);
            List<EvtpLinePoints> evtpLinePoints = evtpLinePointsService.selectList(evtpLinePointsWrapper);
            for (EvtpLinePoints linePoints : evtpLinePoints) {
                MapPoint mapPoint = new MapPoint();
                mapPoint.setLat(Double.valueOf(linePoints.getLat()));
                mapPoint.setLng(Double.valueOf(linePoints.getLng()));
                mapPoints.add(mapPoint);
            }
            List<MapPoint> detailPints = PointsUtil.getDetailPints(mapPoints, 0.0001, 0.0001);
            mapPointQueue.addAll(detailPints);
            int size = detailPints.size();
            Integer runTime = evtpLine.getRunTime();
            double distance = evtpLine.getDistance();
            double totlePowerConsumption = ArithUtil.mul(powerConsumptionPerKilometer, distance);
            double unitPowerConsumption = ArithUtil.div(totlePowerConsumption, (size - 1));
            int delay = runTime % (size - 1);
            int period = runTime / (size - 1);
            String userId = evtpLine.getOwerId();
            EvtpUser evtpUser = evtpUserService.selectById(userId);
            String evId = evtpUser.getEvId();
            EvtpElectricVehicle evtpElectricVehicle = evtpElectricVehicleService.selectById(evId);
            mapLine.setMapPoints(mapPointQueue);
            mapLine.setEvtpLine(evtpLine);
            mapLine.setEvtpElectricVehicle(evtpElectricVehicle);
            MapGoChargingTask mapGoChargingTask = new MapGoChargingTask();
            mapGoChargingTask.setMapLine(mapLine);
            mapGoChargingTask.setWebSocket(webSocket);
            mapGoChargingTask.setEvtpLineService(evtpLineService);
            mapGoChargingTask.setEvtpTransactionService(evtpTransactionService);
            mapGoChargingTask.setEvtpElectricVehicleService(evtpElectricVehicleService);
            String startTime = evtpLine.getStartTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long time = 0;
            try {
                time = sdf.parse(startTime).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long l = System.currentTimeMillis();
            System.err.println("***********************************************");
            System.out.println(time - l);
            System.out.println(size);
            System.err.println("***********************************************");
            mapGoChargingTask.setMapQueue(webPointsTask.getMapQueue());
            Timer timer = new Timer();
            mapGoChargingTask.setTimer(timer);
            mapGoChargingTask.setDistance(distance);
            mapGoChargingTask.setUnitPowerConsumption(unitPowerConsumption);
            mapGoChargingTask.setRedisCacheService(redisCacheService);
//            timer.schedule(mapGoChargingTask, 0, period);
            try {
                ElectricVehiclePowerUtil.setDisChargingInfo(evtpElectricVehicle.getCurrentPower(),evtpLine.getDistance(),evtpLine.getRunTime(),size,evtpLine.getOwerId());
                timer.schedule(mapGoChargingTask, 0, 100);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
