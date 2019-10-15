package com.behaviour;

import com.agent.ElectricVehicleAgent;
import com.view.Screen;
import jade.core.behaviours.Behaviour;

import java.util.Date;

public class ElectricVehicleBehaviour extends Behaviour {
    private static final long serialVersionUID = 1L;
    private Screen screen;
    private int id;
    private String plateNo;
    private String brand;
    private String model;
    private int chargingUserId;
    private Date createTime;
    private String status;
    private Double power;
    private Double batteryCapacity;
    private String position;
    private String positionVal;
    private Double speed;
    private String remark;

    public ElectricVehicleBehaviour(ElectricVehicleAgent electricVehicleAgent) {
        this.batteryCapacity = electricVehicleAgent.getBatteryCapacity();
        this.brand = electricVehicleAgent.getBrand();
        this.screen = electricVehicleAgent.getScreen();
        this.id = electricVehicleAgent.getId();
        this.plateNo = electricVehicleAgent.getPlateNo();
        this.model = electricVehicleAgent.getModel();
        this.chargingUserId = electricVehicleAgent.getChargingUserId();
        this.createTime = electricVehicleAgent.getCreateTime();
        this.status = electricVehicleAgent.getStatus();
        this.power = electricVehicleAgent.getPower();
        this.batteryCapacity = electricVehicleAgent.getBatteryCapacity();
        this.position = electricVehicleAgent.getPosition();
        this.positionVal = electricVehicleAgent.getPositionVal();
        this.speed = electricVehicleAgent.getSpeed();
        this.remark = electricVehicleAgent.getRemark();
    }

    @Override
    public void action() {

    }

    @Override
    public boolean done() {
        return false;
    }
}
