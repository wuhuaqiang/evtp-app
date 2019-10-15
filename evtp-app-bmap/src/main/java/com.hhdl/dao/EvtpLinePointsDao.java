package com.hhdl.dao;

import com.hhdl.model.EvtpLinePoints;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-08-06
 */
public interface EvtpLinePointsDao extends BaseMapper<EvtpLinePoints> {
    int delAll();
}
