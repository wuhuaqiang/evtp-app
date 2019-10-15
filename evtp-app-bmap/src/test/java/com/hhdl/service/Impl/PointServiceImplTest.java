package com.hhdl.service.Impl;

import com.hhdl.service.PointService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointServiceImplTest {
    @Autowired
    private PointService pointService;

    @Test
    public void updateAllPoints() {
        pointService.updateAllPoints();
    }
}