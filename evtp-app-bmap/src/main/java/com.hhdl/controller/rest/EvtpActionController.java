package com.hhdl.controller.rest;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.common.model.CommonResult;
import com.hhdl.endpoint.WebSocket;
import com.hhdl.model.EvtpChargingParking;
import com.hhdl.service.EvtpActionService;
import com.hhdl.service.EvtpChargingParkingService;
import com.hhdl.service.EvtpLinePointsService;
import com.hhdl.service.EvtpLineService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-08-05
 */
@CrossOrigin
@RestController
@RequestMapping("/evtpAction")
public class EvtpActionController {
    @Autowired
    private EvtpActionService evtpActionService;

    @RequestMapping("/acton")
    public void acton() throws Exception {
        evtpActionService.acton();
    }
}

