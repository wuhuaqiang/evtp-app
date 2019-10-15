package com.hhdl.service;

import com.baomidou.mybatisplus.service.IService;
import com.hhdl.model.TLine;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-11-26
 */
public interface TLineService extends IService<TLine> {
    public List<TLine> selectLineByUserId(String userId);
}
