package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.model.TChargingPile;
import com.hhdl.service.TChargingPileService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2018-12-03
 */
@RestController
@RequestMapping("/tChargingPile")
public class TChargingPileController {
    @Autowired
    private TChargingPileService tChargingPileService;

    @RequestMapping("/list")
    public List<TChargingPile> getPage() {
        Wrapper<TChargingPile> tChargingPileWrapper = new EntityWrapper<TChargingPile>();
        return tChargingPileService.selectList(tChargingPileWrapper);
    }

    @RequestMapping("/save")
    public String saveLine(@RequestBody TChargingPile tChargingPile) {
        try {
            if (tChargingPile.getId() == null) {
                tChargingPile.setId(UUIDKey.getKey());
                tChargingPile.setCreateTime(new Date());
            }
            tChargingPileService.insertOrUpdate(tChargingPile);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/delbyId")
    public String delbyId(@RequestBody String id) {
        try {
            tChargingPileService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

}

