package com.hhdl.service;

import com.baomidou.mybatisplus.service.IService;
import com.hhdl.model.TElectricVehicle;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-03
 */
public interface TElectricVehicleService extends IService<TElectricVehicle> {
    List<Map> selectListWithUserId();
}
