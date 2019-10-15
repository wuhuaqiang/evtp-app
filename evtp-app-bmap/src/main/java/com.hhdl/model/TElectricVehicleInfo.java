package com.hhdl.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2019-06-19
 */
@TableName("t_electric_vehicle_info")
public class TElectricVehicleInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("id")
    private String id;
    @TableField("car_name")
    private String carName;
    @TableField("car_type")
    private String carType;
    private String price;
    @TableField("dynamic_type")
    private String dynamicType;
    private String quality;
    @TableField("battery_capacity")
    private String batteryCapacity;
    @TableField("charging_time")
    private String chargingTime;
    @TableField("power_consumption")
    private String powerConsumption;
    @TableField("maximum_range")
    private String maximumRange;
    private Date created;
    private Date updated;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDynamicType() {
        return dynamicType;
    }

    public void setDynamicType(String dynamicType) {
        this.dynamicType = dynamicType;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(String batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getChargingTime() {
        return chargingTime;
    }

    public void setChargingTime(String chargingTime) {
        this.chargingTime = chargingTime;
    }

    public String getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(String powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public String getMaximumRange() {
        return maximumRange;
    }

    public void setMaximumRange(String maximumRange) {
        this.maximumRange = maximumRange;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "TElectricVehicleInfo{" +
                "id=" + id +
                ", carName=" + carName +
                ", carType=" + carType +
                ", price=" + price +
                ", dynamicType=" + dynamicType +
                ", quality=" + quality +
                ", batteryCapacity=" + batteryCapacity +
                ", chargingTime=" + chargingTime +
                ", powerConsumption=" + powerConsumption +
                ", maximumRange=" + maximumRange +
                ", created=" + created +
                ", updated=" + updated +
                "}";
    }
}
