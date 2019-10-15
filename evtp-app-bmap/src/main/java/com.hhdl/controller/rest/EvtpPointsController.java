package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.common.model.CommonResult;
import com.hhdl.model.EvtpPoints;
import com.hhdl.model.TChargingParking;
import com.hhdl.service.EvtpPointsService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/evtpPoints")
public class EvtpPointsController {
    @Autowired
    private EvtpPointsService evtpPointsService;

    @RequestMapping("/list")
    public CommonResult getPage() {
        Wrapper<EvtpPoints> evtpPointsWrapper = new EntityWrapper<EvtpPoints>();
        return CommonResult.success(evtpPointsService.selectList(evtpPointsWrapper));
    }

    @RequestMapping("/getOneById")
    public CommonResult getOneById(@RequestParam String id) {
        EvtpPoints evtpPoints = evtpPointsService.selectById(id);
        return CommonResult.success(evtpPoints);
    }

    @RequestMapping("/save")
    public CommonResult save(@RequestBody EvtpPoints evtpPoints) {
        try {
            evtpPoints.setId(UUIDKey.getKey());
            System.out.println(evtpPoints);
            evtpPointsService.insert(evtpPoints);
        } catch (Exception e) {
            System.out.println(e);
        }

        return CommonResult.success("");
    }

    @RequestMapping("/saveAll")
    public CommonResult saveAll(@RequestBody List<EvtpPoints> evtpPoints) {
        try {
            evtpPointsService.insertBatch(evtpPoints);
        } catch (Exception e) {
            System.out.println(e);
        }

        return CommonResult.success("");
    }

    @RequestMapping("/delAll")
    public CommonResult delAll() {
        try {
            evtpPointsService.delAll();
        } catch (Exception e) {
            System.out.println(e);
        }

        return CommonResult.success("");
    }
}

