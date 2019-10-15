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
 * @since 2019-01-21
 */
@TableName("t_power_history")
public class TPowerHistory extends Model<TPowerHistory> {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("ower_id")
    private String owerId;
    @TableField("ev_id")
    private String evId;
    private Double power;
    private String time;
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwerId() {
        return owerId;
    }

    public void setOwerId(String owerId) {
        this.owerId = owerId;
    }

    public String getEvId() {
        return evId;
    }

    public void setEvId(String evId) {
        this.evId = evId;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TPowerHistory{" +
                "id=" + id +
                ", owerId=" + owerId +
                ", evId=" + evId +
                ", power=" + power +
                ", time=" + time +
                ", remark=" + remark +
                "}";
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
