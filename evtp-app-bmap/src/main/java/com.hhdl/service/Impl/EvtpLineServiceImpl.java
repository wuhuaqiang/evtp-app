package com.hhdl.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hhdl.dao.EvtpLineDao;
import com.hhdl.model.EvtpLine;
import com.hhdl.service.EvtpLineService;
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
public class EvtpLineServiceImpl extends ServiceImpl<EvtpLineDao, EvtpLine> implements EvtpLineService {
    @Autowired
    private EvtpLineDao evtpLineDao;

    @Override
    public int delAll() {
        return evtpLineDao.delAll();
    }
}
