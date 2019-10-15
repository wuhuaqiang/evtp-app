package com.hhdl.util;

import com.hhdl.model.EvtpChargingStation;
import com.hhdl.model.EvtpElectricVehicle;

import java.util.List;

public class NearestChargingStationUtil {
    public static EvtpChargingStation nearestChargingStation(EvtpElectricVehicle evtpElectricVehicle, List<EvtpChargingStation> evtpChargingStations) {
        int minIndex = 0;
        double min = Double.MIN_VALUE;
        for (int i = 0; i < evtpChargingStations.size(); i++) {
            String[] splitEv = evtpElectricVehicle.getPositionVal().split(",");
            String[] splitCs = evtpChargingStations.get(i).getPositionVal().split(",");
            double latEv = Double.valueOf(splitEv[0]);
            double lngEv = Double.valueOf(splitEv[1]);
            double latCs = Double.valueOf(splitCs[0]);
            double lngCs = Double.valueOf(splitCs[1]);
            double lat = ArithUtil.sub(latEv, latCs);
            double lng = ArithUtil.sub(lngEv, lngCs);
            double absLat = Math.abs(lat);
            double absLng = Math.abs(lng);
            double powLat = Math.pow(absLat, 2);
            double powLng = Math.pow(absLng, 2);
            double powLngLat = powLat + powLng;
            if (i == 0) {
                min = powLngLat;
            } else if (min > powLngLat) {
                min = powLngLat;
                minIndex = i;
            }
        }
        return evtpChargingStations.get(minIndex);
    }
}
