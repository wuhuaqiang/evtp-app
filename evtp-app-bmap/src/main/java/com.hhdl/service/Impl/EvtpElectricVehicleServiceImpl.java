package com.hhdl.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hhdl.dao.EvtpElectricVehicleDao;
import com.hhdl.model.EvtpElectricVehicle;
import com.hhdl.service.EvtpElectricVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-08-05
 */
@Service
public class EvtpElectricVehicleServiceImpl extends ServiceImpl<EvtpElectricVehicleDao, EvtpElectricVehicle> implements EvtpElectricVehicleService {
    @Autowired
    private EvtpElectricVehicleDao evtpElectricVehicleDao;

    @Override
    public int delAll() {
        return evtpElectricVehicleDao.delAll();
    }
}
