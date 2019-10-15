package com.hhdl.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.model.TDestination;
import com.hhdl.model.TPoint;
import com.hhdl.service.TDestinationService;
import com.hhdl.service.TPointService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DestinationUtilTest {

    @Autowired
    private TDestinationService tDestinationService;
    @Autowired
    private TPointService tPointService;

    @Test
    public void createDestinations() {
        List<TDestination> destinations = DestinationUtil.createDestinations();
        Wrapper<TPoint> wrapper = new EntityWrapper<TPoint>();
        List<TPoint> tPoints = tPointService.selectList(wrapper);
        List<Point> points = new ArrayList<Point>();
        for (TPoint tPoint : tPoints) {
            Point point = new Point(Double.valueOf(tPoint.getLat()), Double.valueOf(tPoint.getLag()));
            points.add(point);
        }
        for (TDestination tDestination : destinations) {
            System.out.println(tDestination);
            // 104.09565,30.570682  104.069401,30.571895 104.154597,30.576201
            Point point = new Point(Double.valueOf(tDestination.getLat()), Double.valueOf(tDestination.getLng()));
//            if (MapUtil.isPointInPolygon(point, points)) {
//                tDestinationService.insertOrUpdate(tDestination);
//            }

        }
        System.out.println(destinations);
    }
}