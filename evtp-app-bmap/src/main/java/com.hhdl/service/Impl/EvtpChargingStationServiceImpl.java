package com.hhdl.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hhdl.dao.EvtpChargingPileDao;
import com.hhdl.dao.EvtpChargingStationDao;
import com.hhdl.model.EvtpChargingStation;
import com.hhdl.service.EvtpChargingStationService;
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
public class EvtpChargingStationServiceImpl extends ServiceImpl<EvtpChargingStationDao, EvtpChargingStation> implements EvtpChargingStationService {
    @Autowired
    private EvtpChargingStationDao evtpChargingStationDao;

    @Override
    public int delAll() {
        return evtpChargingStationDao.delAll();
    }
}
