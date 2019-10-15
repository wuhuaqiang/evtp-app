package com.hhdl.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hhdl.dao.EvtpChargingPileDao;
import com.hhdl.model.EvtpChargingPile;
import com.hhdl.service.EvtpChargingPileService;
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
public class EvtpChargingPileServiceImpl extends ServiceImpl<EvtpChargingPileDao, EvtpChargingPile> implements EvtpChargingPileService {
    @Autowired
    private EvtpChargingPileDao evtpChargingPileDao;

    @Override
    public int delAll() {
        return evtpChargingPileDao.delAll();
    }
}
