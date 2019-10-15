package com.hhdl.controller.rest;

import com.hhdl.common.model.CommonResult;
import com.hhdl.service.EvtpInitMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evtpInitMap")
public class EvtpInitMapController {

    @Autowired
    private EvtpInitMapService evtpInitMapService;

    @RequestMapping("/initMap")
    public CommonResult init() {
        try {
            evtpInitMapService.initMapParameter();
        } catch (Exception e) {
            return CommonResult.failed();
        }
        return CommonResult.success("成功");
    }
}
