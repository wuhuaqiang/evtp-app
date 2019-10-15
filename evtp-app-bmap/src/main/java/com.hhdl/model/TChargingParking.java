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
 * @since 2019-04-16
 */
@TableName("t_charging_parking")
public class TChargingParking implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private Double number;
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

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
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
        return "TChargingParking{" +
        "id=" + id +
        ", name=" + name +
        ", number=" + number +
        ", csId=" + csId +
        ", state=" + state +
        "}";
    }
}
