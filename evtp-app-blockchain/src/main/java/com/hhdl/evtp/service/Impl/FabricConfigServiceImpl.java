package com.hhdl.evtp.service.Impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.evtp.dao.FabricConfigMapper;
import com.hhdl.evtp.model.FabricConfigModel;
import com.hhdl.evtp.service.FabricConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class FabricConfigServiceImpl implements FabricConfigService {
    @Autowired
    private FabricConfigMapper fabricConfigMapper;

    @Override
    public int save(FabricConfigModel fabricConfigModel) {
        return fabricConfigMapper.addFabricConfig(fabricConfigModel);
    }

    @Override
    public int update(FabricConfigModel fabricConfigModel) {
        return fabricConfigMapper.modifyFabricConfig(fabricConfigModel);
    }

    @Override
    public int del(Serializable id) {
        FabricConfigModel fabricConfigModel = fabricConfigMapper.selectById(id);
        fabricConfigModel.setIs_delete(true);
        return fabricConfigMapper.modifyFabricConfig(fabricConfigModel);
    }

    @Override
    public List<FabricConfigModel> list(FabricConfigModel fabricConfigModel) {
        return fabricConfigMapper.queryAllFabricConfig();
    }
}
