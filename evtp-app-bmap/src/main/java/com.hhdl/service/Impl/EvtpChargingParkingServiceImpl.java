package com.hhdl.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hhdl.dao.EvtpChargingParkingDao;
import com.hhdl.model.EvtpChargingParking;
import com.hhdl.service.EvtpChargingParkingService;
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
public class EvtpChargingParkingServiceImpl extends ServiceImpl<EvtpChargingParkingDao, EvtpChargingParking> implements EvtpChargingParkingService {
    @Autowired
    private EvtpChargingParkingDao evtpChargingParkingDao;

    @Override
    public int delAll() {
        return evtpChargingParkingDao.delAll();
    }
}
