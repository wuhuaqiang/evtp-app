package com.hhdl.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hhdl.dao.TLineDao;
import com.hhdl.model.TLine;
import com.hhdl.service.TLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-11-26
 */
@Service
public class TLineServiceImpl extends ServiceImpl<TLineDao, TLine> implements TLineService {
    @Autowired
    private TLineDao tLineDao;

    @Override
    public List<TLine> selectLineByUserId(String userId) {
        return tLineDao.selectLineByUserId(userId);
    }
}
