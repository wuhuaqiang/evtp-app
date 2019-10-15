package com.hhdl.service;

import com.hhdl.model.EvtpChargingParking;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-08-05
 */
public interface EvtpChargingParkingService extends IService<EvtpChargingParking> {
    int delAll();
}
