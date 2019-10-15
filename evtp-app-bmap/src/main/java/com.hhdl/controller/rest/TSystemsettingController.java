package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.model.TSystemsetting;
import com.hhdl.service.TSystemsettingService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-01-07
 */
@RestController
@RequestMapping("/tSystemsetting")
public class TSystemsettingController {
    @Autowired
    private TSystemsettingService tSystemsettingService;
//    @Autowired
//    private QuartzScheduler quartzScheduler;


    @RequestMapping("/list")
    public List<TSystemsetting> getList()  {//throws SchedulerException
        Wrapper<TSystemsetting> tUserWrapper = new EntityWrapper<TSystemsetting>();
//        quartzScheduler.modifyJob("job", "group", "0/20 * * * * ?");
        return tSystemsettingService.selectList(tUserWrapper);
    }

    @RequestMapping("/save")
    public String saveUser(@RequestBody TSystemsetting tSystemsetting) {
        try {
            if (tSystemsetting.getId() == null) {
                tSystemsetting.setId(UUIDKey.getKey());
            }
//            quartzScheduler.modifyJob("job", "group", "0/40 * * * * ?");
            tSystemsettingService.insertOrUpdate(tSystemsetting);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/update")
    public String updateUser(@RequestBody TSystemsetting tSystemsetting) {
        try {
            tSystemsettingService.insertOrUpdate(tSystemsetting);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/delbyId")
    public String delbyId(@RequestBody String id) {
        try {
            tSystemsettingService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "success";
    }
}

