package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hhdl.common.model.CommonResult;
import com.hhdl.model.EvtpElectricVehicle;
import com.hhdl.model.EvtpPoints;
import com.hhdl.model.EvtpUser;
import com.hhdl.mybeanutils.MyBeanUtils;
import com.hhdl.service.EvtpElectricVehicleService;
import com.hhdl.service.EvtpPointsService;
import com.hhdl.service.EvtpUserService;
import com.hhdl.service.Impl.RedisCacheService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-08-05
 */
@RestController
@RequestMapping("/evtpUser")
public class EvtpUserController {
    @Autowired
    private EvtpUserService evtpUserService;
    @Autowired
    private EvtpPointsService evtpPointsService;
    @Autowired
    private EvtpElectricVehicleService evtpElectricVehicleService;
    @Autowired
    private RedisCacheService redisCacheService;

    @RequestMapping("/page")
    public CommonResult page(@RequestParam int page, int limit) {
        Page<EvtpUser> evtpUserPage = new Page<>(page, limit);
        return CommonResult.success(evtpUserService.selectPage(evtpUserPage));
    }

    @RequestMapping("/list")
    public CommonResult list() {
        EvtpElectricVehicle evtpElectricVehicle = null;
        List result = new ArrayList();
        Wrapper<EvtpUser> evtpUserWrapper = new EntityWrapper<EvtpUser>();
        List<EvtpUser> evtpUsers = evtpUserService.selectList(evtpUserWrapper);
        for (EvtpUser evtpUser : evtpUsers) {
            if (redisCacheService.hasKey("evtp:"+evtpUser.getId())) {
                evtpElectricVehicle = (EvtpElectricVehicle) redisCacheService.get("evtp:"+evtpUser.getId());
            } else {
                evtpElectricVehicle = evtpElectricVehicleService.selectById(evtpUser.getEvId());
            }
            EvtpPoints evtpPoints_home = evtpPointsService.selectById(evtpUser.getHomePositionId());
            EvtpPoints evtpPoints_company = evtpPointsService.selectById(evtpUser.getCompanyPositionId());
            String otherPositionId = evtpUser.getOtherPositionId();
            String[] split = otherPositionId.split(",");
            List<String> otherPositionVal = new ArrayList<String>();
            for (String s : split) {
                EvtpPoints evtpPoints_other = evtpPointsService.selectById(s);
                otherPositionVal.add(evtpPoints_other.getLat() + "," + evtpPoints_company.getLng());
            }
            Map<String, Object> stringObjectMap = MyBeanUtils.bean2map(evtpUser);
            stringObjectMap.put("homePositionVal", evtpPoints_home.getLat() + "," + evtpPoints_home.getLng());
            stringObjectMap.put("companyPositionVal", evtpPoints_company.getLat() + "," + evtpPoints_company.getLng());
            stringObjectMap.put("otherPositionVal", otherPositionVal);
            stringObjectMap.put("currPositionVal", evtpElectricVehicle.getPositionVal());
            stringObjectMap.put("current_power",evtpElectricVehicle.getCurrentPower());
//            redisCacheService.set(evtpUser.getId(), evtpElectricVehicle, 24 * 60 * 60);
            result.add(stringObjectMap);
        }

        return CommonResult.success(result);
    }

    @RequestMapping("/save")
    public CommonResult save(@RequestBody EvtpUser evtpUser) {
        try {
            if (evtpUser.getId() == null || "".equals(evtpUser.getId())) {
                evtpUser.setId(UUIDKey.getKey());
            }
            evtpUserService.insertOrUpdate(evtpUser);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CommonResult.success("");
    }

    @RequestMapping("/update")
    public CommonResult update(@RequestBody EvtpUser evtpUser) {
        try {
            evtpUserService.insertOrUpdate(evtpUser);
        } catch (Exception e) {
            System.out.println(e);
        }

        return CommonResult.success("更新成功");
    }

    @RequestMapping("/del")
    public CommonResult del(@RequestParam String id) {
        try {
            evtpUserService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CommonResult.success("删除成功");
    }
}

