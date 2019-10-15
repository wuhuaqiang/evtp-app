package com.hhdl.system.service;

import com.hhdl.common.model.GetEasyUIData;
import com.hhdl.system.model.Dict;
import com.hhdl.system.model.DictPage;

import java.util.List;

public interface DictService {

    GetEasyUIData findTByPage(DictPage dict);

    void insertDict(Dict dict);

    void updateByPrimaryKeyDict(Dict dict);

    void deleteByPrimaryKeyDict(String[] id);

    Dict selectByPrimaryKey(String id);

    List<Dict> findTByT(Dict dict);
}
