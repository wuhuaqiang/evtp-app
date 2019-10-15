package com.hhdl.model;

import java.util.Queue;

public class MapLine {
    private EvtpLine evtpLine;
    private Queue<MapPoint> mapPoints;
    private EvtpElectricVehicle evtpElectricVehicle;

    public EvtpLine getEvtpLine() {
        return evtpLine;
    }

    public void setEvtpLine(EvtpLine evtpLine) {
        this.evtpLine = evtpLine;
    }

    public Queue<MapPoint> getMapPoints() {
        return mapPoints;
    }

    public void setMapPoints(Queue<MapPoint> mapPoints) {
        this.mapPoints = mapPoints;
    }

    public EvtpElectricVehicle getEvtpElectricVehicle() {
        return evtpElectricVehicle;
    }

    public void setEvtpElectricVehicle(EvtpElectricVehicle evtpElectricVehicle) {
        this.evtpElectricVehicle = evtpElectricVehicle;
    }

    @Override
    public String toString() {
        return "MapLine{" +
                "evtpLine=" + evtpLine +
                ", mapPoints=" + mapPoints +
                ", evtpElectricVehicle=" + evtpElectricVehicle +
                '}';
    }
}
