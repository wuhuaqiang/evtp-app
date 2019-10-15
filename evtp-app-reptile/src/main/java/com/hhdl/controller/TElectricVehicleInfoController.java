package com.hhdl.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hhdl.common.model.CommonResult;
import com.hhdl.model.TElectricVehicleInfo;
import com.hhdl.service.TElectricVehicleInfoService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tElectricVehicleInfo")
public class TElectricVehicleInfoController {

    @Autowired
    private TElectricVehicleInfoService tElectricVehicleInfoService;

    @RequestMapping("/list")
    public CommonResult getList() {
        List<TElectricVehicleInfo> tElectricVehicleInfos = tElectricVehicleInfoService.selectList(new EntityWrapper<TElectricVehicleInfo>());
        return CommonResult.success(tElectricVehicleInfos);
    }

    @RequestMapping("/save")
    public CommonResult saveLine(@RequestBody TElectricVehicleInfo tElectricVehicleInfo) {
        try {
            tElectricVehicleInfoService.insertOrUpdate(tElectricVehicleInfo);
            return CommonResult.success("成功!");
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }


    }

    @RequestMapping("/delbyId")
    public CommonResult delbyId(@RequestBody String id) {
        try {
            tElectricVehicleInfoService.deleteById(id);
            return CommonResult.success("成功!");
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
    }
}
