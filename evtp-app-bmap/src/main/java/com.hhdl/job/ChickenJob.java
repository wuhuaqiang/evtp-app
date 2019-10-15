package com.hhdl.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hhdl.endpoint.WebSocket;
import com.hhdl.model.MapPoint;
import com.hhdl.model.RunTask;
import com.hhdl.model.TElectricVehicleLocation;
import com.hhdl.service.BMapService;
import com.hhdl.service.TElectricVehicleService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.BlockingQueue;

@Component
@DisallowConcurrentExecution
public class ChickenJob implements Job, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ChickenJob.class);
    private static final long serialVersionUID = 1L;
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private TElectricVehicleService tElectricVehicleService;
    @Autowired
    private BMapService bMapService;
    private static List<Map> mapList = null;
    private static List<Map> result = new ArrayList<>();
    private static RunTask runTask = new RunTask();


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
      /*  System.err.println("*********************************");
        System.out.println("Hello!  NewJob is executing." + new Date());
        System.err.println("*********************************");
        TElectricVehicleLocation tElectricVehicleLocation = null;
        try {

            if (runTask.getId() == null) {
                List<MapPoint> linePoints = bMapService.getLinePoints("30.650556,104.080748", "30.640415,104.035689");
                runTask.setId("13e3c560bfff490eb0c59026ce816c15");
                runTask.setOwerId("13e3c560bfff490eb0c59026ce816c15");
                Queue<MapPoint> queue = new LinkedList<MapPoint>();
                for (MapPoint point : linePoints) {
                    queue.add(point);
                }
                runTask.setContent(queue);
                runTask.setMapPoint(((LinkedList<MapPoint>) queue).getLast());

            } else {
                tElectricVehicleLocation = new TElectricVehicleLocation();
                tElectricVehicleLocation.setCarId(runTask.getId());
                MapPoint mapPoint = runTask.getContent().poll();
                if (mapPoint != null) {
                    tElectricVehicleLocation.setMapPoint(mapPoint);
                } else {
                    tElectricVehicleLocation.setMapPoint(runTask.getMapPoint());
                }

            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        if (mapList == null) {
            mapList = tElectricVehicleService.getAllTElectricVehicleWithLine();
        }
        if (result.size() == 0) {
            for (Map obj : mapList) {
                String[] positionVals = obj.get("positionVal").toString().split(",");
                Map map = new HashMap();
                Map point = new HashMap();
                point.put("lng", Double.valueOf(positionVals[0]));
                point.put("lat", Double.valueOf(positionVals[1]));
                map.put("id", obj.get("id").toString());
                map.put("userId", obj.get("userId").toString());
                map.put("point", point);
                result.add(map);
            }
        } else {
            for (Map obj : result) {
                Map point = (Map) obj.get("point");
                Double lng = (Double) point.get("lng");
                point.put("lng", lng + 0.0001);
                Double lat = (Double) point.get("lat");
                point.put("lat", lat + 0.0001);
            }
        }
        JSONArray arrays = JSONArray.parseArray(JSON.toJSONString(result));

      *//*  for (const item of tElectricVehicles){
            console.log(item)
            const positionArr = item.positionVal.split(',')
            const point = {
                    lng:parseFloat(positionArr[0]),
                    lat:parseFloat(positionArr[1])
            }
            const marker = {id:item.id, userId:item.userId, point:point, label:{
                content:
                item.userName, opts:{
                    offset:
                    {
                        width:
                        20, height:0
                    },position:
                    point, enableMassClear:true
                }
            }}
            this.tElectricVehiclePoints.push(marker)
        }*//*

        try {
            if (tElectricVehicleLocation != null) {
//                String s = JSON.toJSONString(tElectricVehicleLocation);
                webSocket.sendInfo("msg");
            }
//            webSocket.sendInfo(arrays.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //取得job详情
        JobDetail jobDetail = context.getJobDetail();
        // 取得job名称
        String jobName = jobDetail.getClass().getName();
        logger.info("Name: " + jobDetail.getClass().getSimpleName());
        //取得job的类
        logger.info("Job Class: " + jobDetail.getJobClass());
        //取得job开始时间
        logger.info(jobName + " fired at " + context.getFireTime());
        //取得job下次触发时间
        logger.info("Next fire time " + context.getNextFireTime());

        JobDataMap dataMap = jobDetail.getJobDataMap();

        logger.info("itstyle: " + dataMap.getString("itstyle"));
        logger.info("blog: " + dataMap.getString("blog"));
        String[] array = (String[]) dataMap.get("data");
        if (array != null && array.length > 0) {
            for (String str : array) {
                logger.info("data: " + str);
            }
        }*/
//        //测试效果 保证上一个任务执行完后，再去执行下一个任务
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}
