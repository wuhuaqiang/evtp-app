package com.hhdl.service.Impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hhdl.dao.TElectricVehicleDao;
import com.hhdl.model.TElectricVehicle;
import com.hhdl.model.TLine;
import com.hhdl.service.TElectricVehicleService;
import com.hhdl.service.TLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-03
 */
@Service
public class TElectricVehicleServiceImpl extends ServiceImpl<TElectricVehicleDao, TElectricVehicle> implements TElectricVehicleService {
    @Autowired
    private TElectricVehicleDao tElectricVehicleDao;
    @Autowired
    private TLineService tLineService;

    @Override
    public List<Map> selectListWithUserId() {
        return tElectricVehicleDao.selectListWithUserId();
    }

    @Override
    public List<Map> getAllTElectricVehicleWithLine() {
        List<Map> mapList = this.selectListWithUserId();
        for (Map map : mapList) {
            String userId = map.get("userId").toString();
            Wrapper<TLine> tLineWrapper = new EntityWrapper<TLine>();
            tLineWrapper.where("ower_id={0}", userId).orderBy("sort", true);
            List<TLine> tLines = tLineService.selectList(tLineWrapper);
            map.put("lines", tLines);
        }
        return mapList;
    }
}
