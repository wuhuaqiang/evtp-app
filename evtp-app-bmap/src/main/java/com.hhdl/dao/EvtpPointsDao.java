package com.hhdl.dao;

import com.hhdl.model.EvtpPoints;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-08-01
 */
public interface EvtpPointsDao extends BaseMapper<EvtpPoints> {
    int delAll();

}
