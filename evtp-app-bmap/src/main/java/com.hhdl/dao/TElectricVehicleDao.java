package com.hhdl.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hhdl.model.TElectricVehicle;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-12-03
 */
public interface TElectricVehicleDao extends BaseMapper<TElectricVehicle> {
    List<Map> selectListWithUserId();
}
