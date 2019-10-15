package com.hhdl.dao;

import com.hhdl.model.EvtpUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-08-05
 */
public interface EvtpUserDao extends BaseMapper<EvtpUser> {
    int delAll();
}
