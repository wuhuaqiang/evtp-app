package com.hhdl.model;

public class TChargingRecodeWithName extends TChargingRecode {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TChargingRecodeWithName{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
