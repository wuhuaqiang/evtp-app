package com.hhdl.evtp.service.Impl;

import com.hhdl.evtp.dao.FabricConfigMapper;
import com.hhdl.evtp.model.FabricConfigOrdererModel;
import com.hhdl.evtp.model.FabricConfigPeerModel;
import com.hhdl.evtp.service.FabricOrdererService;
import com.hhdl.evtp.service.FabricPeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class FabricPeerServiceImpl implements FabricPeerService {
    @Autowired
    private FabricConfigMapper fabricConfigMapper;


    @Override
    public int save(FabricConfigPeerModel fabricConfigPeerModel) {
        return fabricConfigMapper.addFabricPeer(fabricConfigPeerModel);
    }

    @Override
    public int update(FabricConfigPeerModel fabricConfigPeerModel) {
        return fabricConfigMapper.modifyFabricPeer(fabricConfigPeerModel);
    }

    @Override
    public int del(Serializable id) {
        return fabricConfigMapper.deleteFabricPeer((Integer) id);
    }

    @Override
    public List<FabricConfigPeerModel> list(FabricConfigPeerModel fabricConfigPeerModel) {
        return fabricConfigMapper.queryAllFabricPeer();
    }
}
