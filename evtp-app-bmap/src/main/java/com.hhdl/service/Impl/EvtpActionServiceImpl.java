package com.hhdl.service.Impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.endpoint.WebSocket;
import com.hhdl.entity.*;
import com.hhdl.model.*;
import com.hhdl.service.*;
import com.hhdl.util.ArithUtil;
import com.hhdl.util.ElectricVehiclePowerUtil;
import com.hhdl.util.PointsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyStore;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class EvtpActionServiceImpl implements EvtpActionService {
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private EvtpLineService evtpLineService;
    @Autowired
    private EvtpChargingStationService evtpChargingStationService;
    @Autowired
    private EvtpSimulationParametersService evtpSimulationParametersService;
    @Autowired
    private EvtpLinePointsService evtpLinePointsService;
    @Autowired
    private EvtpElectricVehicleService evtpElectricVehicleService;
    @Autowired
    private EvtpUserService evtpUserService;
    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private EvtpTransactionService evtpTransactionService;
    @Autowired
    private EvtpPointsService evtpPointsService;
    //    @Autowired
//    private WebPointsTask webPointsTask;
//    @Autowired
//    private CheckCharging checkCharging;
//    @Autowired
//    private CheckOtherTask checkOtherTask;
//    @Autowired
//    private CreateTask createTask;
    private Timer timerWebPoint = null;
    private Timer timerCheckCharging = null;
    private Timer timerCheckOtherTask = null;
    private Timer timerCreateTask = null;
    private MapPointTask mapPointTask = null;
    private WebPointsTask webPointsTask = null;
    private CheckCharging checkCharging = null;
    private CheckOtherTask checkOtherTask = null;
    private CreateTask createTask = null;
    private MapContinueTask mapContinueTask = null;
    private MapGoChargingTask mapGoChargingTask = null;

    @Override
    public int acton() throws ParseException {
        if (timerWebPoint == null) {
            timerWebPoint = new Timer();
            timerCheckCharging = new Timer();
            timerCheckOtherTask = new Timer();
            timerCreateTask = new Timer();
            webPointsTask = new WebPointsTask();
            webPointsTask.setWebSocket(webSocket);
//            mapPointTask = new MapPointTask();
            mapContinueTask = getMapContinueTask();
            mapGoChargingTask = getMapGoChargingTask();
            checkOtherTask = getCheckOtherTask();
            createTask = getCreateTask();
            checkCharging = getCheckCharging();
            List<MapLine> mapLines = new ArrayList<>();
            redisCacheService.delAllKey("evtp");
            Wrapper<EvtpChargingStation> evtpChargingStationWrapper = new EntityWrapper<EvtpChargingStation>();
            List<EvtpChargingStation> evtpChargingStations = evtpChargingStationService.selectList(evtpChargingStationWrapper);
            redisCacheService.lSet("evtp:evtpChargingStations", evtpChargingStations);
            Wrapper<EvtpLine> evtpLineWrapper = new EntityWrapper<EvtpLine>();
            List<EvtpLine> evtpLines = evtpLineService.selectList(evtpLineWrapper);
            Wrapper<EvtpSimulationParameters> parametersWrapper = new EntityWrapper<>();
            List<EvtpSimulationParameters> evtpSimulationParameters = evtpSimulationParametersService.selectList(parametersWrapper);
            EvtpSimulationParameters parameters = evtpSimulationParameters.get(0);
            redisCacheService.set("evtp:evtpSimulationParameter", parameters);
            double powerConsumptionPerKilometer = Double.valueOf(parameters.getPowerConsumptionPerKilometer());
            for (EvtpLine evtpLine : evtpLines) {
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
                evtpElectricVehicle.setRunState("1");
                mapLine.setEvtpElectricVehicle(evtpElectricVehicle);
                MapPointTask mapPointTask = new MapPointTask();
                mapPointTask.setMapLine(mapLine);
                mapPointTask.setWebSocket(webSocket);
                mapPointTask.setEvtpLineService(evtpLineService);
                mapPointTask.setEvtpElectricVehicleService(evtpElectricVehicleService);
                String startTime = evtpLine.getStartTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long time = sdf.parse(startTime).getTime();
                long l = System.currentTimeMillis();
                System.err.println("***********************************************");
                System.out.println(time - l);
                System.out.println(size);
                System.err.println("***********************************************");
                Timer timer = new Timer();
                mapPointTask.setMapQueue(webPointsTask.getMapQueue());
                mapPointTask.setTimer(timer);
                mapPointTask.setDistance(distance);
                mapPointTask.setUnitPowerConsumption(unitPowerConsumption);
                mapPointTask.setRedisCacheService(redisCacheService);
//            timer.schedule(mapPointTask, (time - l) + delay, period);
                try {
                    ElectricVehiclePowerUtil.setDisChargingInfo(evtpElectricVehicle.getCurrentPower(), evtpLine.getDistance(), evtpLine.getRunTime(), size, evtpLine.getOwerId());
                    timer.schedule(mapPointTask, (time - l) + delay, 100);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            createTask.setMapQueue(webPointsTask.getMapQueue());
            timerWebPoint.schedule(webPointsTask, 0, 100);
            timerCheckCharging.schedule(checkCharging, 0, 30000);
            timerCheckOtherTask.schedule(checkOtherTask, 0, 30000);
            timerCreateTask.schedule(createTask, 240000, 120000);
/*        for (EvtpLine evtpLine : evtpLines) {
//            String userId = evtpLine.getOwerId();
//            EvtpUser evtpUser = evtpUserService.selectById(userId);
//            String evId = evtpUser.getEvId();
//            EvtpElectricVehicle evtpElectricVehicle = evtpElectricVehicleService.selectById(evId);
            MapLine mapLine = new MapLine();
            mapLine.setEvtpLine(evtpLine);
//            mapLine.setEvtpElectricVehicle(evtpElectricVehicle);
            Wrapper<EvtpLinePoints> evtpLinePointsWrapper = new EntityWrapper<EvtpLinePoints>();
            evtpLinePointsWrapper.where("line_id={0}", evtpLine.getId()).orderBy("sort", true);
            List<EvtpLinePoints> evtpLinePoints = evtpLinePointsService.selectList(evtpLinePointsWrapper);
            Queue<MapPoint> mapPointQueue = new LinkedBlockingDeque<>();
            for (EvtpLinePoints linePoints : evtpLinePoints) {
                MapPoint mapPoint = new MapPoint();
                mapPoint.setLat(Double.valueOf(linePoints.getLat()));
                mapPoint.setLng(Double.valueOf(linePoints.getLng()));
                mapPointQueue.add(mapPoint);
            }
            mapLine.setMapPoints(mapPointQueue);
            mapLines.add(mapLine);
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<Map> mapList = new ArrayList<>();
                for (MapLine mapLine : mapLines) {
                    Map<String, Object> param = new HashMap<>();
                    EvtpLine evtpLine = mapLine.getEvtpLine();
                    Queue<MapPoint> mapPoints = mapLine.getMapPoints();
                    param.put("id", evtpLine.getOwerId());
                    if (mapPoints.size() > 0) {
                        MapPoint mapPoint = mapPoints.remove();
                        param.put("point", mapPoint);
                        mapList.add(param);
                    }

                }
                System.out.println(mapList);
//                evtpElectricVehicle.setPositionVal(mapPoint.getLng() + "," + mapPoint.getLat());
//                JSONArray objects = JSONArraymm .parseArray(JSON.toJSONString(evtpElectricVehicle));
                try {
                    webSocket.sendInfo(JSON.toJSONString(mapList));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1000, 100);*/
            return 0;
        } else {
            mapPointTask.cancel();
            webPointsTask.cancel();
            checkCharging.cancel();
            checkOtherTask.cancel();
            createTask.cancel();
            mapContinueTask.cancel();
            mapGoChargingTask.cancel();
            timerWebPoint.cancel();
            timerWebPoint.purge();
            timerCheckCharging.cancel();
            timerCheckCharging.purge();
            timerCheckOtherTask.cancel();
            timerCheckOtherTask.purge();
            timerCreateTask.cancel();
            timerCreateTask.purge();
            timerWebPoint = null;
            timerCheckCharging = null;
            timerCheckOtherTask = null;
            timerCreateTask = null;
            return 1;
        }
    }

    private CheckOtherTask getCheckOtherTask() {
        CheckOtherTask checkOtherTask = new CheckOtherTask();
        checkOtherTask.setEvtpElectricVehicleService(evtpElectricVehicleService);
        checkOtherTask.setEvtpLineService(evtpLineService);
        checkOtherTask.setEvtpUserService(evtpUserService);
        checkOtherTask.setRedisCacheService(redisCacheService);
        checkOtherTask.setEvtpSimulationParametersService(evtpSimulationParametersService);
        checkOtherTask.setWebSocket(webSocket);
        checkOtherTask.setMapGoChargingTask(mapGoChargingTask);
        checkOtherTask.setWebPointsTask(webPointsTask);
        checkOtherTask.setEvtpLinePointsService(evtpLinePointsService);
        return checkOtherTask;
    }

    private MapGoChargingTask getMapGoChargingTask() {
        MapGoChargingTask mapGoChargingTask = new MapGoChargingTask();
        mapGoChargingTask.setEvtpElectricVehicleService(evtpElectricVehicleService);
        mapGoChargingTask.setEvtpLineService(evtpLineService);
        mapGoChargingTask.setRedisCacheService(redisCacheService);
        mapGoChargingTask.setWebSocket(webSocket);
        mapGoChargingTask.setMapContinueTask(mapContinueTask);
        mapGoChargingTask.setEvtpTransactionService(evtpTransactionService);
        return mapGoChargingTask;
    }

    private MapContinueTask getMapContinueTask() {
        MapContinueTask mapContinueTask = new MapContinueTask();
        mapContinueTask.setEvtpElectricVehicleService(evtpElectricVehicleService);
        mapContinueTask.setEvtpLineService(evtpLineService);
        mapContinueTask.setRedisCacheService(redisCacheService);
        mapContinueTask.setWebSocket(webSocket);
        return mapContinueTask;
    }

    private CreateTask getCreateTask() {
        CreateTask createTask = new CreateTask();
        createTask.setEvtpElectricVehicleService(evtpElectricVehicleService);
        createTask.setEvtpLineService(evtpLineService);
        createTask.setRedisCacheService(redisCacheService);
        createTask.setWebSocket(webSocket);
        createTask.setEvtpPointsService(evtpPointsService);
        createTask.setEvtpUserService(evtpUserService);
        return createTask;
    }

    private CheckCharging getCheckCharging() {
        CheckCharging checkCharging = new CheckCharging();
        checkCharging.setEvtpElectricVehicleService(evtpElectricVehicleService);
        checkCharging.setEvtpLineService(evtpLineService);
        checkCharging.setRedisCacheService(redisCacheService);
        checkCharging.setWebSocket(webSocket);
        checkCharging.setEvtpLinePointsService(evtpLinePointsService);
        checkCharging.setEvtpUserService(evtpUserService);
        checkCharging.setEvtpSimulationParametersService(evtpSimulationParametersService);
        checkCharging.setMapGoChargingTask(mapGoChargingTask);
        checkCharging.setWebPointsTask(webPointsTask);
        checkCharging.setEvtpTransactionService(evtpTransactionService);
        return checkCharging;
    }
}
