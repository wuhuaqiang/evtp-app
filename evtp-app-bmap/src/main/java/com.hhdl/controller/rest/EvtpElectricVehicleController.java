package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.common.model.CommonResult;
import com.hhdl.model.EvtpElectricVehicle;
import com.hhdl.service.EvtpElectricVehicleService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-08-05
 */
@RestController
@RequestMapping("/evtpElectricVehicle")
public class EvtpElectricVehicleController {
    @Autowired
    private EvtpElectricVehicleService evtpElectricVehicleService;

    @RequestMapping("/list")
    public CommonResult getPage() {
        Wrapper<EvtpElectricVehicle> evtpElectricVehicleWrapper = new EntityWrapper<EvtpElectricVehicle>();
        return CommonResult.success(evtpElectricVehicleService.selectList(evtpElectricVehicleWrapper));
    }

    @RequestMapping("/save")
    public CommonResult save(@RequestBody EvtpElectricVehicle evtpElectricVehicle) {
        try {

            if (evtpElectricVehicle.getId() == null || "".equals(evtpElectricVehicle.getId())) {
                evtpElectricVehicle.setId(UUIDKey.getKey());
            }
            evtpElectricVehicleService.insertOrUpdate(evtpElectricVehicle);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CommonResult.success("");
    }

    @RequestMapping("/update")
    public CommonResult update(@RequestBody EvtpElectricVehicle evtpElectricVehicle) {
        try {
            evtpElectricVehicleService.insertOrUpdate(evtpElectricVehicle);
        } catch (Exception e) {
            System.out.println(e);
        }

        return CommonResult.success("更新成功");
    }

    @RequestMapping("/del")
    public CommonResult del(@RequestParam String id) {
        try {
            evtpElectricVehicleService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CommonResult.success("删除成功");
    }
}

