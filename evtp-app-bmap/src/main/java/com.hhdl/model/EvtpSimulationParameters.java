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
 * @since 2019-08-02
 */
@TableName("evtp_simulation_parameters")
public class EvtpSimulationParameters implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 电池容量
     */
    @TableField("battery_capacity")
    private String batteryCapacity;
    /**
     * 电动汽车数量
     */
    @TableField("electric_vehicle_number")
    private Integer electricVehicleNumber;
    /**
     * 充电效率
     */
    @TableField("charging_efficiency")
    private String chargingEfficiency;
    /**
     * 电动汽车耗电量
     */
    @TableField("power_consumption_per_kilometer")
    private String powerConsumptionPerKilometer;
    /**
     * 充电速度
     */
    @TableField("charging_speed")
    private String chargingSpeed;
    /**
     * 充电站数量
     */
    @TableField("charging_station_number")
    private Integer chargingStationNumber;
    /**
     * 每个充电站的充电桩数量
     */
    @TableField("charging_pile_number")
    private Integer chargingPileNumber;
    /**
     * 每个充电站的停车位数量
     */
    @TableField("parking_space_number")
    private Integer parkingSpaceNumber;
    /**
     * 交易策略
     */
    @TableField("transaction_strategy")
    private String transactionStrategy;
    /**
     * 模拟步长
     */
    private Integer step;
    /**
     * 备注
     */
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(String batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public Integer getElectricVehicleNumber() {
        return electricVehicleNumber;
    }

    public void setElectricVehicleNumber(Integer electricVehicleNumber) {
        this.electricVehicleNumber = electricVehicleNumber;
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

    public String getChargingSpeed() {
        return chargingSpeed;
    }

    public void setChargingSpeed(String chargingSpeed) {
        this.chargingSpeed = chargingSpeed;
    }

    public Integer getChargingStationNumber() {
        return chargingStationNumber;
    }

    public void setChargingStationNumber(Integer chargingStationNumber) {
        this.chargingStationNumber = chargingStationNumber;
    }

    public Integer getChargingPileNumber() {
        return chargingPileNumber;
    }

    public void setChargingPileNumber(Integer chargingPileNumber) {
        this.chargingPileNumber = chargingPileNumber;
    }

    public Integer getParkingSpaceNumber() {
        return parkingSpaceNumber;
    }

    public void setParkingSpaceNumber(Integer parkingSpaceNumber) {
        this.parkingSpaceNumber = parkingSpaceNumber;
    }

    public String getTransactionStrategy() {
        return transactionStrategy;
    }

    public void setTransactionStrategy(String transactionStrategy) {
        this.transactionStrategy = transactionStrategy;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "EvtpSimulationParameters{" +
        "id=" + id +
        ", batteryCapacity=" + batteryCapacity +
        ", electricVehicleNumber=" + electricVehicleNumber +
        ", chargingEfficiency=" + chargingEfficiency +
        ", powerConsumptionPerKilometer=" + powerConsumptionPerKilometer +
        ", chargingSpeed=" + chargingSpeed +
        ", chargingStationNumber=" + chargingStationNumber +
        ", chargingPileNumber=" + chargingPileNumber +
        ", parkingSpaceNumber=" + parkingSpaceNumber +
        ", transactionStrategy=" + transactionStrategy +
        ", step=" + step +
        ", remark=" + remark +
        "}";
    }
}
