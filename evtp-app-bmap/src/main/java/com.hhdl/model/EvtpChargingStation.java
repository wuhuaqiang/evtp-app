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
@TableName("evtp_charging_station")
public class EvtpChargingStation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private Double capacity;
    @TableField("pile_number")
    private Integer pileNumber;
    private String position;
    @TableField("position_val")
    private String positionVal;
    private String state;
    @TableField("charging_efficiency")
    private Float chargingEfficiency;
    @TableField("create_time")
    private Date createTime;
    @TableField("run_time")
    private Date runTime;
    private String account;


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

    public Integer getPileNumber() {
        return pileNumber;
    }

    public void setPileNumber(Integer pileNumber) {
        this.pileNumber = pileNumber;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "EvtpChargingStation{" +
        "id=" + id +
        ", name=" + name +
        ", capacity=" + capacity +
        ", pileNumber=" + pileNumber +
        ", position=" + position +
        ", positionVal=" + positionVal +
        ", state=" + state +
        ", chargingEfficiency=" + chargingEfficiency +
        ", createTime=" + createTime +
        ", runTime=" + runTime +
        ", account=" + account +
        "}";
    }
}
