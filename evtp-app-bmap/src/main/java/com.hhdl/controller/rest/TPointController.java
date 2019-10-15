package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.model.TPoint;
import com.hhdl.service.TPointService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-03-18
 */
@RestController
@RequestMapping("/tPoint")
public class TPointController {
    @Autowired
    private TPointService tPointService;

    @RequestMapping("/list")
    public List<TPoint> getPage() throws Exception {
        Wrapper<TPoint> tLineWrapper = new EntityWrapper<TPoint>();
        return tPointService.selectList(tLineWrapper);
    }

    @RequestMapping("/save")
    public String saveLine(@RequestBody TPoint tPoint) {
        try {
            if (tPoint.getId() == null) {
                tPoint.setId(UUIDKey.getKey());
            }
            tPointService.insertOrUpdate(tPoint);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

}

