package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.common.model.CommonResult;
import com.hhdl.model.EvtpChargingStation;
import com.hhdl.service.EvtpChargingStationService;
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
@RequestMapping("/evtpChargingStation")
public class EvtpChargingStationController {
    @Autowired
    private EvtpChargingStationService evtpChargingStationService;

    @RequestMapping("/list")
    public CommonResult list() {
        Wrapper<EvtpChargingStation> evtpChargingStationWrapper = new EntityWrapper<EvtpChargingStation>();
        return CommonResult.success(evtpChargingStationService.selectList(evtpChargingStationWrapper));
    }

    @RequestMapping("/save")
    public CommonResult save(@RequestBody EvtpChargingStation evtpChargingStation) {
        try {
            if (evtpChargingStation.getId() == null || "".equals(evtpChargingStation.getId())) {
                evtpChargingStation.setId(UUIDKey.getKey());
            }
            evtpChargingStationService.insertOrUpdate(evtpChargingStation);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CommonResult.success("");
    }

    @RequestMapping("/update")
    public CommonResult update(@RequestBody EvtpChargingStation evtpChargingStation) {
        try {
            evtpChargingStationService.insertOrUpdate(evtpChargingStation);
        } catch (Exception e) {
            System.out.println(e);
        }

        return CommonResult.success("更新成功");
    }

    @RequestMapping("/del")
    public CommonResult del(@RequestParam String id) {
        try {
            evtpChargingStationService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CommonResult.success("删除成功");
    }
}

