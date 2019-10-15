package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.common.model.CommonResult;
import com.hhdl.model.*;
import com.hhdl.mybeanutils.MyBeanUtils;
import com.hhdl.service.BMapService;
import com.hhdl.service.Impl.RedisCacheService;
import com.hhdl.service.TElectricVehicleService;
import com.hhdl.service.TLineService;
import com.hhdl.service.TUserService;
import com.hhdl.util.PointsUtil;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2018-12-03
 */
@RestController
@RequestMapping("/tElectricVehicle")
public class TElectricVehicleController {
    @Autowired
    private TLineService tLineService;
    @Autowired
    private TUserService tUserService;
    @Autowired
    private TElectricVehicleService tElectricVehicleService;
    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private BMapService bMapService;

    @RequestMapping("/list")
    public CommonResult getPage() {
        Wrapper<TElectricVehicle> tElectricVehicleWrapper = new EntityWrapper<TElectricVehicle>();
        List<TElectricVehicle> result = tElectricVehicleService.selectList(tElectricVehicleWrapper);
        return CommonResult.success(result);
    }

    @RequestMapping("/save")
    public String saveLine(@RequestBody TElectricVehicle tElectricVehicle) {
        try {
            if (tElectricVehicle.getId() == null) {
                tElectricVehicle.setId(UUIDKey.getKey());
            }
            tElectricVehicleService.insertOrUpdate(tElectricVehicle);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/update")
    public String updateLine(@RequestBody TElectricVehicle tElectricVehicle) {
        try {
            TElectricVehicle electricVehicle = null;
            boolean ret = redisCacheService.hasKey(tElectricVehicle.getId());
            if (ret) {
                electricVehicle = (TElectricVehicle) redisCacheService.get(tElectricVehicle.getId());
            } else {
                electricVehicle = tElectricVehicleService.selectById(tElectricVehicle.getId());
            }
//            TElectricVehicle electricVehicle = tElectricVehicleService.selectById(tElectricVehicle.getId());
            electricVehicle.setPositionVal(tElectricVehicle.getPositionVal());
            electricVehicle.setPosition(tElectricVehicle.getPosition());
            electricVehicle.setPosition(tElectricVehicle.getPosition());
            electricVehicle.setPower(tElectricVehicle.getPower() + electricVehicle.getPower());
            if ("mysql".equals(tElectricVehicle.getRemark())) {
                tElectricVehicleService.insertOrUpdate(electricVehicle);
            }
            redisCacheService.set(electricVehicle.getId(), electricVehicle);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/delbyId")
    public String delbyId(@RequestBody String id) {
        try {
            tElectricVehicleService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "success";
    }

    @RequestMapping("/getAllListWithLine")
    public CommonResult getAllTElectricVehicleWithLine() {
        /* List<TElectricVehicleWithLine> list = new ArrayList<TElectricVehicleWithLine>();*/
//        Wrapper<TElectricVehicle> tElectricVehicleEntityWrapper = new EntityWrapper<TElectricVehicle>();
        List<Map> mapList = tElectricVehicleService.getAllTElectricVehicleWithLine();
        try {
//            bMapService.getNearestChargingStation("08264b41b18c40c782216e1c4c9c9c2a");
//            List<MapPoint> linePoints = bMapService.getLinePoints("104.080748,30.650556", "104.035689,30.640415");

//            List<MapPoint> detailPints = PointsUtil.getDetailPints(linePoints, 0.0001, 0.0001);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return CommonResult.success(mapList);

    }

    @RequestMapping("/getEVWithLineById")
    public TElectricVehicleWithLine getElectricVehicleWithLineById(@RequestBody Map<String, String> param) {
        TElectricVehicleWithLine vehicleWithLine = new TElectricVehicleWithLine();
        try {
            TUser tUser = tUserService.selectById(param.get("userId"));
            TElectricVehicle tElectricVehicle = null;
//            TElectricVehicle tElectricVehicle = tElectricVehicleService.selectById(param.get("evId"));
            if (redisCacheService.hasKey("evId")) {
                tElectricVehicle = (TElectricVehicle) redisCacheService.get(param.get("evId"));
            } else {
                tElectricVehicle = tElectricVehicleService.selectById(param.get("evId"));
            }

            Wrapper<TLine> tLineWrapper = new EntityWrapper<TLine>();
            tLineWrapper.where("ower_id={0}", param.get("userId")).orderBy("sort", true);
            List<TLine> tLines = tLineService.selectList(tLineWrapper);
            MyBeanUtils.copyProperties(tElectricVehicle, vehicleWithLine);
            vehicleWithLine.setUserName(tUser.getName());
            vehicleWithLine.settLines(tLines);

        } catch (Exception e) {

        }

        return vehicleWithLine;

    }

    @RequestMapping("/getEVById")
    public TElectricVehicle getElectricVehicleById(@RequestBody Map<String, String> param) {
        TElectricVehicle tElectricVehicle = null;
        boolean ret = redisCacheService.hasKey(param.get("id"));
        if (ret) {
            tElectricVehicle = (TElectricVehicle) redisCacheService.get(param.get("id"));
        } else {
            tElectricVehicle = tElectricVehicleService.selectById(param.get("id"));
        }
        return tElectricVehicle;
    }
}

