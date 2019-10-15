package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.model.TTask;
import com.hhdl.service.TElectricVehicleService;
import com.hhdl.service.TTaskService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/tTask")
public class TTaskController {
    @Autowired
    private TTaskService tTaskService;
    @Autowired
    private TElectricVehicleService tElectricVehicleService;

    @RequestMapping("/list")
    public List<TTask> getList() {
        Wrapper<TTask> tTaskWrapper = new EntityWrapper<TTask>();
        return tTaskService.selectList(tTaskWrapper);
    }

    @RequestMapping("/currTaskList")
    public List<TTask> getcurrTaskList(@RequestBody String owerIds) {
        List<TTask> tTasks = new ArrayList<TTask>();
        String[] owerIdsArr = owerIds.split(",");
        for (int i = 0; i < owerIdsArr.length; i++) {
            TTask owerTask = tTaskService.getOwerTask(owerIdsArr[i]);
            if (owerTask != null) {
                tTasks.add(owerTask);
            }
        }
        return tTasks;
    }

    @RequestMapping("/lastTaskByOwerId")
    public TTask lastTaskByOwerId(@RequestBody String owerId) {
        return tTaskService.getOwerNewTask(owerId);
    }

    @RequestMapping("/currTaskByOwerId")
    public TTask currTaskByOwerId(@RequestBody String owerId) {
        return tTaskService.getOwerTask(owerId);
    }

    @RequestMapping("/save")
    public String saveTask(@RequestBody TTask tTask) {
        try {
            if (tTask.getId() == null) {
                tTask.setId(UUIDKey.getKey());
            }
            tTaskService.insert(tTask);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/allsave")
    public String saveAllTask(@RequestParam Integer sort) {

        try {
            List<Map> mapList = tElectricVehicleService.selectListWithUserId();
            long i = 600000;
            int j = mapList.size();
            for (Map map : mapList) {
                TTask tTask = new TTask();
                tTask.setId(UUIDKey.getKey());
                tTask.setTime(i++);
                tTask.setSort(sort);
                tTask.setOwerId(map.get("userId").toString());
                tTask.setState("0");
                tTask.setPoint(mapList.get(j-1).get("positionVal").toString());
                tTaskService.insert(tTask);
                j--;
            }


        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/update")
    public String updateTask(@RequestBody TTask tTask) {
        try {
            tTaskService.insertOrUpdate(tTask);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/close")
    public String closeTask(@RequestBody String owerId) {
        try {
            TTask tTask = tTaskService.selectById(owerId);
            tTask.setState("1");
            tTaskService.insertOrUpdate(tTask);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/delbyId")
    public String delbyId(@RequestBody String id) {
        try {
            tTaskService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "success";
    }


}

