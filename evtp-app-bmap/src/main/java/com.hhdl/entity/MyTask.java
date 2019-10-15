package com.hhdl.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.endpoint.WebSocket;
import com.hhdl.model.*;
import com.hhdl.service.BMapService;
import com.hhdl.service.TElectricVehicleService;
import com.hhdl.service.TPointService;
import com.hhdl.service.TTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class MyTask extends TimerTask {
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private TPointService tPointService;
    @Autowired
    private BMapService bMapService;
    @Autowired
    private TTaskService tTaskService;
    @Autowired
    private TElectricVehicleService tElectricVehicleService;

    private static List<RunTask> runTasks;
    private static List<Map> mapList;
    private static Queue<TTask> tTaskQueue;

    public static List<RunTask> getRunTasks() {
        return runTasks;
    }

    public static void setRunTasks(List<RunTask> runTasks) {
        MyTask.runTasks = runTasks;
    }

    public static List<Map> getMapList() {
        return mapList;
    }

    public static void setMapList(List<Map> mapList) {
        MyTask.mapList = mapList;
    }

    public static Queue<TTask> gettTaskQueue() {
        return tTaskQueue;
    }

    public static void settTaskQueue(Queue<TTask> tTaskQueue) {
        MyTask.tTaskQueue = tTaskQueue;
    }

    @Override
    public void run() {
        List<TElectricVehicleLocation> tElectricVehicleLocations = new ArrayList<TElectricVehicleLocation>();

        try {
            for (RunTask runTask : runTasks) {
                TElectricVehicleLocation tElectricVehicleLocation = new TElectricVehicleLocation();
                MapPoint mapPoint = runTask.getContent().poll();
                if (mapPoint != null) {
                    tElectricVehicleLocation.setCarId(runTask.getCarId());
                    tElectricVehicleLocation.setUserId(runTask.getUserId());
                    tElectricVehicleLocation.setMapPoint(mapPoint);
                    tElectricVehicleLocations.add(tElectricVehicleLocation);
                    TElectricVehicle tElectricVehicle = tElectricVehicleService.selectById(runTask.getCarId());
                    tElectricVehicle.setPositionVal(mapPoint.getLng() + "," + mapPoint.getLat());
                    tElectricVehicleService.insertOrUpdate(tElectricVehicle);
                } else {
                    runTasks.remove(runTask);
                    TTask tTask = tTaskService.selectById(runTask.getTaskId());
                    tTask.setState("1");
                    tTaskService.insertOrUpdate(tTask);
                    TTask owerNewTask = tTaskService.getOwerNewTask(runTask.getUserId());
                    if (owerNewTask != null) {
                        tTaskQueue.add(tTask);
                        RunTask newrunTask = new RunTask();
                        TElectricVehicle tElectricVehicle = tElectricVehicleService.selectById(runTask.getCarId());
//                        String[] positionVals = tElectricVehicle.getPositionVal().toString().split(",");
//                        String origin = positionVals[1] + "," + positionVals[0];
//                        String[] split = tTask.getPoint().split(",");
//                        String destination = split[1] + "," + split[0];
                        List<MapPoint> linePoints = bMapService.getLinePoints(tElectricVehicle.getPositionVal(), tTask.getPoint());
                        Queue<MapPoint> queue = new LinkedList<MapPoint>();
                        for (MapPoint point : linePoints) {
                            queue.add(point);
                        }
                        newrunTask.setCarId(runTask.getCarId());
                        newrunTask.setUserId(runTask.getUserId());
                        newrunTask.setTaskId(tTask.getId());
                        newrunTask.setContent(queue);
                        newrunTask.setMapPoint(((LinkedList<MapPoint>) queue).getLast());
                        runTasks.add(newrunTask);
                    }
                    //ToDo
                }


            }

            try {
                if (tElectricVehicleLocations.size() > 0) {
                    JSONArray objects = JSONArray.parseArray(JSON.toJSONString(tElectricVehicleLocations));
                    webSocket.sendInfo(objects.toJSONString());
                }
//            webSocket.sendInfo(arrays.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    private TTask getOwerTask(String owerId) {
        Wrapper<TTask> tTaskWrapper = new EntityWrapper<TTask>();
        tTaskWrapper.where("ower_id={0}", owerId).and("state=0")
                .orderBy("sort", true);
        List<TTask> tTasks = tTaskService.selectList(tTaskWrapper);
        if (tTasks.size() > 0) {
            return tTasks.get(0);
        } else {
            return null;
        }

    }
}
