package com.hhdl.service;

import com.hhdl.model.EvtpLinePoints;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-08-06
 */
public interface EvtpLinePointsService extends IService<EvtpLinePoints> {
    int delAll();
}
