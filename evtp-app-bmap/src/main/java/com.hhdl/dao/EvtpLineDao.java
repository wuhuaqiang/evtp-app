package com.hhdl.dao;

import com.hhdl.model.EvtpLine;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-08-06
 */
public interface EvtpLineDao extends BaseMapper<EvtpLine> {
    int delAll();
}
