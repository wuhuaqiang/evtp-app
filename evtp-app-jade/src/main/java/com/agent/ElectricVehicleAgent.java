package com.agent;

import com.baomidou.mybatisplus.annotations.TableField;
import com.behaviour.DeviceBehaviour;
import com.view.Screen;
import jade.core.Agent;

import java.util.Date;

public class ElectricVehicleAgent extends Agent {
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

    @Override
    protected void setup() {
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getChargingUserId() {
        return chargingUserId;
    }

    public void setChargingUserId(int chargingUserId) {
        this.chargingUserId = chargingUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPositionVal() {
        return positionVal;
    }

    public void setPositionVal(String positionVal) {
        this.positionVal = positionVal;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
