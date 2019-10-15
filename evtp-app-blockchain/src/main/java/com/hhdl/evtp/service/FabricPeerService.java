package com.hhdl.evtp.service;

import com.hhdl.evtp.model.FabricConfigOrdererModel;
import com.hhdl.evtp.model.FabricConfigPeerModel;

import java.io.Serializable;
import java.util.List;

public interface FabricPeerService {
    int save(FabricConfigPeerModel fabricConfigPeerModel);

    int update(FabricConfigPeerModel fabricConfigPeerModel);

    int del(Serializable id);

    List<FabricConfigPeerModel> list(FabricConfigPeerModel fabricConfigPeerModel);
}
