package com.hhdl.service.Impl;

import com.hhdl.model.EvtpPoints;
import com.hhdl.dao.EvtpPointsDao;
import com.hhdl.service.EvtpPointsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-08-01
 */
@Service
public class EvtpPointsServiceImpl extends ServiceImpl<EvtpPointsDao, EvtpPoints> implements EvtpPointsService {
    @Autowired
    private EvtpPointsDao evtpPointsDao;

    @Override
    public int delAll() {
        return evtpPointsDao.delAll();
    }
}
