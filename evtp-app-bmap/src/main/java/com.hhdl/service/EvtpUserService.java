package com.hhdl.service;

import com.hhdl.model.EvtpUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-08-05
 */
public interface EvtpUserService extends IService<EvtpUser> {
    int delAll();
}
