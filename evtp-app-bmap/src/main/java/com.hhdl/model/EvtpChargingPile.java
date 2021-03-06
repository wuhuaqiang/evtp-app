package com.hhdl.model;

import java.util.Date;
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
@TableName("evtp_charging_pile")
public class EvtpChargingPile implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private Double capacity;
    private String position;
    @TableField("position_val")
    private String positionVal;
    @TableField("charging_efficiency")
    private Float chargingEfficiency;
    @TableField("create_time")
    private Date createTime;
    @TableField("run_time")
    private Date runTime;
    @TableField("cs_id")
    private String csId;
    private String state;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
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

    public Float getChargingEfficiency() {
        return chargingEfficiency;
    }

    public void setChargingEfficiency(Float chargingEfficiency) {
        this.chargingEfficiency = chargingEfficiency;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

    public String getCsId() {
        return csId;
    }

    public void setCsId(String csId) {
        this.csId = csId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "EvtpChargingPile{" +
        "id=" + id +
        ", name=" + name +
        ", capacity=" + capacity +
        ", position=" + position +
        ", positionVal=" + positionVal +
        ", chargingEfficiency=" + chargingEfficiency +
        ", createTime=" + createTime +
        ", runTime=" + runTime +
        ", csId=" + csId +
        ", state=" + state +
        "}";
    }
}
