package com.hhdl.system.dao;

import com.hhdl.system.model.Resources;

import java.util.List;

public interface ResourcesMapper {
    int deleteByPrimaryKey(String id);

    int insert(Resources record);

    Resources selectByPrimaryKey(String id);

    List<Resources> selectAll();

    int updateByPrimaryKey(Resources record);

    void deleteBy(String[] id);

}