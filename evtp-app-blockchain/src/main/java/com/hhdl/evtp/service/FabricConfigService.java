package com.hhdl.evtp.service;

import com.hhdl.evtp.model.FabricConfigModel;

import java.io.Serializable;
import java.util.List;

public interface FabricConfigService {
    int save(FabricConfigModel fabricConfigModel);

    int update(FabricConfigModel fabricConfigModel);

    int del(Serializable id);

    List<FabricConfigModel> list(FabricConfigModel fabricConfigModel);
}
