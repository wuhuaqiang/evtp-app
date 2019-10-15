package com.hhdl.controller.rest;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.endpoint.WebSocket;
import com.hhdl.entity.MyTask;
import com.hhdl.model.*;
import com.hhdl.service.BMapService;
import com.hhdl.service.TElectricVehicleService;
import com.hhdl.service.TPointService;
import com.hhdl.service.TTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@CrossOrigin
@RestController
public class WebSocketController {
    @Autowired
    public SimpMessagingTemplate template;
    @Autowired
    private TElectricVehicleService tElectricVehicleService;
    @Autowired
    private TTaskService tTaskService;
    @Autowired
    private BMapService bMapService;
    @Autowired
    private MyTask myTask;
    static int timerIntel = 100;


    @RequestMapping("/message")
    public void getMessage(@RequestParam Integer timerIntel) throws Exception {

        List<Map> mapList = tElectricVehicleService.selectListWithUserId();
        myTask.setMapList(mapList);
        Queue<TTask> tTaskQueue = new LinkedList<TTask>();
        List<RunTask> runTasks = new ArrayList<>();
        for (Map map : mapList) {
            TTask tTask = getOwerTask(map.get("userId").toString());
            if (tTask != null) {
                tTaskQueue.add(tTask);
                RunTask runTask = new RunTask();
//                String[] positionVals = map.get("positionVal").toString().split(",");
//                String origin = positionVals[1] + "," + positionVals[0];
//                String[] split = tTask.getPoint().split(",");
//                String destination = split[1] + "," + split[0];
                List<MapPoint> linePoints = bMapService.getLinePoints(map.get("positionVal").toString(), tTask.getPoint());
                Queue<MapPoint> queue = new LinkedList<MapPoint>();
                for (MapPoint point : linePoints) {
                    queue.add(point);
                }
                runTask.setCarId(map.get("id").toString());
                runTask.setUserId(map.get("userId").toString());
                runTask.setTaskId(tTask.getId());
                runTask.setContent(queue);
                runTask.setMapPoint(((LinkedList<MapPoint>) queue).getLast());
                runTasks.add(runTask);
            }

        }
        myTask.settTaskQueue(tTaskQueue);
        myTask.setRunTasks(runTasks);
        final Timer timer = new Timer();
        timer.schedule(myTask, 0, timerIntel);

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
