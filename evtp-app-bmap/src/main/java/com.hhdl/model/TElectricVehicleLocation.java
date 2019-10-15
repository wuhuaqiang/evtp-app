package com.hhdl.model;

import java.io.Serializable;

public class TElectricVehicleLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String carId;
    private String userId;
    private MapPoint mapPoint;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MapPoint getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
    }

    @Override
    public String toString() {
        return "TElectricVehicleLocation{" +
                "carId='" + carId + '\'' +
                ", userId='" + userId + '\'' +
                ", mapPoint=" + mapPoint +
                '}';
    }
}
