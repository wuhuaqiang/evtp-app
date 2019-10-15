package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.common.model.CommonResult;
import com.hhdl.model.EvtpChargingPile;
import com.hhdl.service.EvtpChargingPileService;
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
@RequestMapping("/evtpChargingPile")
public class EvtpChargingPileController {
    @Autowired
    private EvtpChargingPileService evtpChargingPileService;

    @RequestMapping("/list")
    public CommonResult list() {
        Wrapper<EvtpChargingPile> evtpChargingPileWrapper = new EntityWrapper<EvtpChargingPile>();
        return CommonResult.success(evtpChargingPileService.selectList(evtpChargingPileWrapper));
    }

    @RequestMapping("/save")
    public CommonResult save(@RequestBody EvtpChargingPile evtpChargingPile) {
        try {
            if (evtpChargingPile.getId() == null || "".equals(evtpChargingPile.getId())) {
                evtpChargingPile.setId(UUIDKey.getKey());
            }
            evtpChargingPileService.insertOrUpdate(evtpChargingPile);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CommonResult.success("");
    }

    @RequestMapping("/update")
    public CommonResult update(@RequestBody EvtpChargingPile evtpChargingPile) {
        try {
            evtpChargingPileService.insertOrUpdate(evtpChargingPile);
        } catch (Exception e) {
            System.out.println(e);
        }

        return CommonResult.success("更新成功");
    }

    @RequestMapping("/del")
    public CommonResult del(@RequestParam String id) {
        try {
            evtpChargingPileService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CommonResult.success("删除成功");
    }
}

