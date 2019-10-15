package com.hhdl.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-08-05
 */
@TableName("evtp_electric_vehicle")
public class EvtpElectricVehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 当前电量
     */
    @TableField("current_power")
    private Double currentPower;
    /**
     * 电池容量
     */
    @TableField("battery_capacity")
    private Double batteryCapacity;
    /**
     * 位置
     */
    private String position;
    /**
     * 经纬度
     */
    @TableField("position_val")
    private String positionVal;
    /**
     * 当前速度
     */
    @TableField("current_speed")
    private Double currentSpeed;
    /**
     * 最大速度
     */
    @TableField("max_speed")
    private Double maxSpeed;
    /**
     * 充电效率
     */
    @TableField("charging_efficiency")
    private String chargingEfficiency;
    /**
     * 每公里耗电量
     */
    @TableField("power_consumption_per_kilometer")
    private String powerConsumptionPerKilometer;
    /**
     * 总里程
     */
    @TableField("total_mileage")
    private String totalMileage;
    /**
     * 运动状态
     */
    @TableField("run_state")
    private String runState;
    /**
     * 充放电状态
     */
    @TableField("charge_discharge_state")
    private String chargeDischargeState;
    /**
     * 状态
     */
    private String state;
    /**
     * 备注
     */
    private String remark;
    /**
     * 充电速度
     */
    @TableField("charging_speed")
    private String chargingSpeed;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(Double currentPower) {
        this.currentPower = currentPower;
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

    public Double getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(Double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getChargingEfficiency() {
        return chargingEfficiency;
    }

    public void setChargingEfficiency(String chargingEfficiency) {
        this.chargingEfficiency = chargingEfficiency;
    }

    public String getPowerConsumptionPerKilometer() {
        return powerConsumptionPerKilometer;
    }

    public void setPowerConsumptionPerKilometer(String powerConsumptionPerKilometer) {
        this.powerConsumptionPerKilometer = powerConsumptionPerKilometer;
    }

    public String getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(String totalMileage) {
        this.totalMileage = totalMileage;
    }

    public String getRunState() {
        return runState;
    }

    public void setRunState(String runState) {
        this.runState = runState;
    }

    public String getChargeDischargeState() {
        return chargeDischargeState;
    }

    public void setChargeDischargeState(String chargeDischargeState) {
        this.chargeDischargeState = chargeDischargeState;
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

    public String getChargingSpeed() {
        return chargingSpeed;
    }

    public void setChargingSpeed(String chargingSpeed) {
        this.chargingSpeed = chargingSpeed;
    }

    @Override
    public String toString() {
        return "EvtpElectricVehicle{" +
        "id=" + id +
        ", currentPower=" + currentPower +
        ", batteryCapacity=" + batteryCapacity +
        ", position=" + position +
        ", positionVal=" + positionVal +
        ", currentSpeed=" + currentSpeed +
        ", maxSpeed=" + maxSpeed +
        ", chargingEfficiency=" + chargingEfficiency +
        ", powerConsumptionPerKilometer=" + powerConsumptionPerKilometer +
        ", totalMileage=" + totalMileage +
        ", runState=" + runState +
        ", chargeDischargeState=" + chargeDischargeState +
        ", state=" + state +
        ", remark=" + remark +
        ", chargingSpeed=" + chargingSpeed +
        "}";
    }
}
