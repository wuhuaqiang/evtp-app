package com.hhdl.system.service;

import com.hhdl.common.model.JsonTreeData;
import com.hhdl.system.model.Resources;

import java.util.List;

public interface ResourcesService {

    void deleteResources(String[] id);

    void insertResources(Resources resources);

    List<JsonTreeData> findResources();

    List<Resources> findResourcesEMUByResources(String id);

    void updateResources(Resources resources);

    List<Resources> findTByT(Resources resources);

    Resources selectByPrimaryKey(String id);

}
