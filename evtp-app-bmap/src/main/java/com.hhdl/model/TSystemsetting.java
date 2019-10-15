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
 * @since 2019-01-07
 */
@TableName("t_systemsetting")
public class TSystemsetting extends Model<TSystemsetting> {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("time_start")
    private String timeStart;
    private Integer k;
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TSystemsetting{" +
                "id=" + id +
                ", timeStart=" + timeStart +
                ", k=" + k +
                ", remark=" + remark +
                "}";
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
