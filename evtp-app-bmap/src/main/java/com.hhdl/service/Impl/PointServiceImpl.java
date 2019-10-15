package com.hhdl.service.Impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.model.TElectricVehicle;
import com.hhdl.service.PointService;
import com.hhdl.service.TElectricVehicleService;
import com.hhdl.util.PointUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointServiceImpl implements PointService {
    @Autowired
    private TElectricVehicleService tElectricVehicleService;

    @Override
    public boolean updateAllPoints() {
        try {
            Wrapper<TElectricVehicle> tElectricVehicleWrapper = new EntityWrapper<TElectricVehicle>();
            List<TElectricVehicle> tElectricVehicles = tElectricVehicleService.selectList(tElectricVehicleWrapper);
            for (TElectricVehicle tElectricVehicle : tElectricVehicles) {
                tElectricVehicle.setPositionVal(PointUtil.getPoint());
                tElectricVehicleService.insertOrUpdate(tElectricVehicle);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
