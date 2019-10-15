package com.hhdl.dao;

import com.hhdl.model.EvtpChargingPile;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-08-05
 */
public interface EvtpChargingPileDao extends BaseMapper<EvtpChargingPile> {
    int delAll();
}
