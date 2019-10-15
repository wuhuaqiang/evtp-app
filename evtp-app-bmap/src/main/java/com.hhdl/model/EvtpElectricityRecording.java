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
 * @since 2019-08-06
 */
@TableName("evtp_electricity_recording")
public class EvtpElectricityRecording implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("ev_id")
    private String evId;
    @TableField("electric_quantity")
    private Double electricQuantity;
    private String remark;
    @TableField("recording_time")
    private Date recordingTime;
    @TableField("user_id")
    private String userId;


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

    public Double getElectricQuantity() {
        return electricQuantity;
    }

    public void setElectricQuantity(Double electricQuantity) {
        this.electricQuantity = electricQuantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRecordingTime() {
        return recordingTime;
    }

    public void setRecordingTime(Date recordingTime) {
        this.recordingTime = recordingTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "EvtpElectricityRecording{" +
        "id=" + id +
        ", evId=" + evId +
        ", electricQuantity=" + electricQuantity +
        ", remark=" + remark +
        ", recordingTime=" + recordingTime +
        ", userId=" + userId +
        "}";
    }
}
