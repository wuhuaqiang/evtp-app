package com.hhdl.service.Impl;

import com.hhdl.model.EvtpPointsType;
import com.hhdl.dao.EvtpPointsTypeDao;
import com.hhdl.service.EvtpPointsTypeService;
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
public class EvtpPointsTypeServiceImpl extends ServiceImpl<EvtpPointsTypeDao, EvtpPointsType> implements EvtpPointsTypeService {
    @Autowired
    private EvtpPointsTypeDao evtpPointsTypeDao;

    @Override
    public Integer getMaxKey() {
        return evtpPointsTypeDao.getMaxKey();
    }
}
