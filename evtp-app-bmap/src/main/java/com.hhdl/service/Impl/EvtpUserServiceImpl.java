package com.hhdl.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hhdl.dao.EvtpUserDao;
import com.hhdl.model.EvtpUser;
import com.hhdl.service.EvtpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-08-05
 */
@Service
public class EvtpUserServiceImpl extends ServiceImpl<EvtpUserDao, EvtpUser> implements EvtpUserService {
    @Autowired
    private EvtpUserDao evtpUserDao;

    @Override
    public int delAll() {
        return evtpUserDao.delAll();
    }
}
