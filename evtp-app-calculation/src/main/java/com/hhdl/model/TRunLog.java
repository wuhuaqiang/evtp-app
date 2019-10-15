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
 * @since 2018-12-28
 */
@TableName("t_run_log")
public class TRunLog extends Model<TRunLog> {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("start_point")
    private String startPoint;
    @TableField("end_point")
    private String endPoint;
    @TableField("start_point_val")
    private String startPointVal;
    @TableField("end_point_val")
    private String endPointVal;
    @TableField("start_time")
    private String startTime;
    @TableField("end_time")
    private String endTime;
    @TableField("ower_id")
    private String owerId;
    private Integer sort;
    private String remark;
    private String state;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getStartPointVal() {
        return startPointVal;
    }

    public void setStartPointVal(String startPointVal) {
        this.startPointVal = startPointVal;
    }

    public String getEndPointVal() {
        return endPointVal;
    }

    public void setEndPointVal(String endPointVal) {
        this.endPointVal = endPointVal;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOwerId() {
        return owerId;
    }

    public void setOwerId(String owerId) {
        this.owerId = owerId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TRunLog{" +
                "id=" + id +
                ", startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                ", startPointVal=" + startPointVal +
                ", endPointVal=" + endPointVal +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", owerId=" + owerId +
                ", sort=" + sort +
                ", remark=" + remark +
                ", state=" + state +
                "}";
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
