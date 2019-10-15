package com.hhdl.evtp.service;

import com.hhdl.evtp.model.FabricConfigModel;
import com.hhdl.evtp.model.FabricConfigOrdererModel;

import java.io.Serializable;
import java.util.List;

public interface FabricOrdererService {
    int save(FabricConfigOrdererModel fabricConfigOrdererModel);

    int update(FabricConfigOrdererModel fabricConfigOrdererModel);

    int del(Serializable id);

    List<FabricConfigOrdererModel> list(FabricConfigOrdererModel fabricConfigOrdererModel);
}
