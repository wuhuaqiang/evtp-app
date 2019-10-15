package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hhdl.model.TRunLog;
import com.hhdl.service.TRunLogService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/tRunLog")
public class TRunLogController {
    @Autowired
    private TRunLogService tRunLogService;

    @RequestMapping("/page")
    public Page getPages(@RequestBody Map map) {
        int pages = (int) map.get("page");
        int size = (int) map.get("size");

        Page<TRunLog> page = new Page<TRunLog>(pages, size);
        Wrapper<TRunLog> tRunLogWrapper = new EntityWrapper<TRunLog>();
        tRunLogWrapper.orderBy("ower_id", true);
        tRunLogWrapper.orderBy("start_time", true);
        return tRunLogService.selectPage(page, tRunLogWrapper);
    }

    @RequestMapping("/list")
    public List<TRunLog> getList() {
        Wrapper<TRunLog> tRunLogWrapper = new EntityWrapper<TRunLog>();
        return tRunLogService.selectList(tRunLogWrapper);
    }

    @RequestMapping("/save")
    public String saveTRunLog(@RequestBody TRunLog tRunLog) {
        try {
            if (tRunLog.getId() == null) {
                tRunLog.setId(UUIDKey.getKey());
            }
            tRunLogService.insertOrUpdate(tRunLog);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/update")
    public String updateTRunLog(@RequestBody TRunLog tRunLog) {
        try {
            TRunLog tRunLogOld = tRunLogService.selectById(tRunLog.getId());
            tRunLogOld.setRemark(tRunLog.getRemark());
            tRunLogOld.setEndPoint(tRunLog.getEndPoint());
            tRunLogOld.setEndPointVal(tRunLog.getEndPointVal());
            tRunLogOld.setEndTime(tRunLog.getEndTime());
            tRunLogOld.setState(tRunLog.getState());
            tRunLogService.insertOrUpdate(tRunLogOld);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/delbyId")
    public String delbyId(@RequestBody String id) {
        try {
            tRunLogService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "success";
    }
}

