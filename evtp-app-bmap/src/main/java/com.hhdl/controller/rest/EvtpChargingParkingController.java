package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.common.model.CommonResult;
import com.hhdl.model.EvtpChargingParking;
import com.hhdl.service.EvtpChargingParkingService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-08-05
 */
@RestController
@RequestMapping("/evtpChargingParking")
public class EvtpChargingParkingController {
    @Autowired
    private EvtpChargingParkingService evtpChargingParkingService;

    @RequestMapping("/list")
    public CommonResult list() {
        Wrapper<EvtpChargingParking> evtpChargingParkingWrapper = new EntityWrapper<EvtpChargingParking>();
        return CommonResult.success(evtpChargingParkingService.selectList(evtpChargingParkingWrapper));
    }

    @RequestMapping("/save")
    public CommonResult save(@RequestBody EvtpChargingParking evtpChargingParking) {
        try {
            if (evtpChargingParking.getId() == null || "".equals(evtpChargingParking.getId())) {
                evtpChargingParking.setId(UUIDKey.getKey());
            }
            evtpChargingParkingService.insertOrUpdate(evtpChargingParking);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CommonResult.success("");
    }

    @RequestMapping("/update")
    public CommonResult update(@RequestBody EvtpChargingParking evtpChargingParking) {
        try {
            evtpChargingParkingService.insertOrUpdate(evtpChargingParking);
        } catch (Exception e) {
            System.out.println(e);
        }

        return CommonResult.success("更新成功");
    }

    @RequestMapping("/del")
    public CommonResult del(@RequestParam String id) {
        try {
            evtpChargingParkingService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CommonResult.success("删除成功");
    }
}

