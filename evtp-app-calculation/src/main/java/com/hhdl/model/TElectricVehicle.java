package com.hhdl.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2018-12-03
 */
@TableName("t_electric_vehicle")
public class TElectricVehicle extends Model<TElectricVehicle> {

    private static final long serialVersionUID = 1L;

    private String id;
    private Double power;
    @TableField("battery_capacity")
    private Double batteryCapacity;
    private String position;
    @TableField("position_val")
    private String positionVal;
    private Double speed;
    private String state;
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TElectricVehicle{" +
                "id=" + id +
                ", power=" + power +
                ", batteryCapacity=" + batteryCapacity +
                ", position=" + position +
                ", positionVal=" + positionVal +
                ", speed=" + speed +
                ", state=" + state +
                ", remark=" + remark +
                "}";
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
