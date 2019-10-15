package com.hhdl.model;

import java.io.Serializable;

public class TElectricVehicleLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String carId;
    private String userId;
    private TPoint tPoint;

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

    public TPoint gettPoint() {
        return tPoint;
    }

    public void settPoint(TPoint tPoint) {
        this.tPoint = tPoint;
    }

    @Override
    public String toString() {
        return "TElectricVehicleLocation{" +
                "carId='" + carId + '\'' +
                ", userId='" + userId + '\'' +
                ", tPoint=" + tPoint +
                '}';
    }
}
