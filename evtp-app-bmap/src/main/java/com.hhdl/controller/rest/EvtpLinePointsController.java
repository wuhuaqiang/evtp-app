package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.common.model.CommonResult;
import com.hhdl.model.EvtpLinePoints;
import com.hhdl.service.EvtpLinePointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-08-06
 */
@RestController
@RequestMapping("/evtpLinePoints")
public class EvtpLinePointsController {
    @Autowired
    private EvtpLinePointsService evtpLinePointsService;

    @RequestMapping("/list")
    public CommonResult getPage() {
        Wrapper<EvtpLinePoints> evtpLinePointsWrapper = new EntityWrapper<EvtpLinePoints>();
        return CommonResult.success(evtpLinePointsService.selectList(evtpLinePointsWrapper));
    }

    @RequestMapping("/getOneById")
    public CommonResult getOneById(@RequestParam String id) {
        EvtpLinePoints evtpLinePoints = evtpLinePointsService.selectById(id);
        return CommonResult.success(evtpLinePoints);
    }

    @RequestMapping("/save")
    public CommonResult save(@RequestBody EvtpLinePoints evtpLinePoints) {
        try {
            evtpLinePointsService.insert(evtpLinePoints);
        } catch (Exception e) {
            System.out.println(e);
        }

        return CommonResult.success("");
    }
    @RequestMapping("/saveAll")
    public CommonResult saveAll(@RequestBody List<EvtpLinePoints> evtpLinePoints) {
        try {
            evtpLinePointsService.insertBatch(evtpLinePoints);
        } catch (Exception e) {
            System.out.println(e);
        }

        return CommonResult.success("");
    }
}

