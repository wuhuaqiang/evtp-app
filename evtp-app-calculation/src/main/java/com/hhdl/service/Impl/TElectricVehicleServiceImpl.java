package com.hhdl.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hhdl.dao.TElectricVehicleDao;
import com.hhdl.model.TElectricVehicle;
import com.hhdl.service.TElectricVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-03
 */
@Service
public class TElectricVehicleServiceImpl extends ServiceImpl<TElectricVehicleDao, TElectricVehicle> implements TElectricVehicleService {
    @Autowired
    private TElectricVehicleDao tElectricVehicleDao;

    @Override
    public List<Map> selectListWithUserId() {
        return tElectricVehicleDao.selectListWithUserId();
    }
}
