package com.hhdl.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2018-12-03
 */
@TableName("t_charging_pile")
public class TChargingPile extends Model<TChargingPile> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCsId() {
        return csId;
    }

    public void setCsId(String csId) {
        this.csId = csId;
    }

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

    @Override
    public String toString() {
        return "TChargingPile{" +
                "id=" + id +
                ", name=" + name +
                ", capacity=" + capacity +
                ", position=" + position +
                ", positionVal=" + positionVal +
                ", chargingEfficiency=" + chargingEfficiency +
                ", createTime=" + createTime +
                ", runTime=" + runTime +
                "}";
    }
}
