package com.hhdl.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hhdl.model.TLine;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-11-26
 */
public interface TLineDao extends BaseMapper<TLine> {
    public List<TLine> selectLineByUserId(String userId);
}
