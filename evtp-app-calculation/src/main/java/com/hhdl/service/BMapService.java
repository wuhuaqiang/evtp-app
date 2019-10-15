package com.hhdl.service;

import com.hhdl.util.Point;

import java.util.List;

public interface BMapService {
    public String getNearestChargingStation(String carId) throws Exception;

    public Boolean ifgoNearestChargingStation(String carId);
}
