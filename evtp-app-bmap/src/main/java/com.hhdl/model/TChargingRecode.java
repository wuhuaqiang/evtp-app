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
@TableName("t_charging_recode")
public class TChargingRecode implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("ev_id")
    private String evId;
    @TableField("cs_id")
    private String csId;
    private String state;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvId() {
        return evId;
    }

    public void setEvId(String evId) {
        this.evId = evId;
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
        return "TChargingRecode{" +
        "id=" + id +
        ", evId=" + evId +
        ", csId=" + csId +
        ", state=" + state +
        "}";
    }
}
