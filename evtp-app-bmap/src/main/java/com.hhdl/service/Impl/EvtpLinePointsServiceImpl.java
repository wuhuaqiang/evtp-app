package com.hhdl.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hhdl.dao.EvtpLinePointsDao;
import com.hhdl.model.EvtpLinePoints;
import com.hhdl.service.EvtpLinePointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-08-06
 */
@Service
public class EvtpLinePointsServiceImpl extends ServiceImpl<EvtpLinePointsDao, EvtpLinePoints> implements EvtpLinePointsService {
    @Autowired
    private EvtpLinePointsDao evtpLinePointsDao;

    @Override
    public int delAll() {
        return evtpLinePointsDao.delAll();
    }
}
