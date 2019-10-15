package com.hhdl.service;


import com.hhdl.model.MapPoint;

import java.util.List;

public interface BMapService {
    public String getNearestChargingStation(String carId) throws Exception;

    public Boolean ifgoNearestChargingStation(String carId);

    public List<MapPoint> getLinePoints(String origin, String destination) throws Exception;
}
