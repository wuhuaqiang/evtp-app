package com.hhdl.evtp.service.Impl;

import com.hhdl.evtp.dao.FabricConfigMapper;
import com.hhdl.evtp.model.FabricConfigOrdererModel;
import com.hhdl.evtp.service.FabricOrdererService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class FabricOrdererServiceImpl implements FabricOrdererService {
    @Autowired
    private FabricConfigMapper fabricConfigMapper;


    @Override
    public int save(FabricConfigOrdererModel fabricConfigOrdererModel) {
        return fabricConfigMapper.addFabricOrderer(fabricConfigOrdererModel);
    }

    @Override
    public int update(FabricConfigOrdererModel fabricConfigOrdererModel) {
        return fabricConfigMapper.modifyFabricOrderer(fabricConfigOrdererModel);
    }

    @Override
    public int del(Serializable id) {
        return fabricConfigMapper.deleteFabricOrderer((Integer) id);
    }

    @Override
    public List<FabricConfigOrdererModel> list(FabricConfigOrdererModel fabricConfigOrdererModel) {
        return fabricConfigMapper.queryAllFabricOrderer();
    }
}
