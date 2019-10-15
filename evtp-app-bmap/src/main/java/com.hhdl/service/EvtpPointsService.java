package com.hhdl.service;

import com.hhdl.model.EvtpPoints;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-08-01
 */
public interface EvtpPointsService extends IService<EvtpPoints> {
    int delAll();
}
