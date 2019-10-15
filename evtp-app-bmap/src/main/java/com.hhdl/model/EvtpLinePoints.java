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
 * @since 2019-08-06
 */
@TableName("evtp_line_points")
public class EvtpLinePoints implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String lat;
    private String lng;
    private Integer type;
    private Integer sort;
    private String remark;
    @TableField("line_id")
    private String lineId;
    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EvtpLinePoints{" +
                "id=" + id +
                ", lat=" + lat +
                ", lng=" + lng +
                ", type=" + type +
                ", remark=" + remark +
                ", lineId=" + lineId +
                ", name=" + name +
                "}";
    }
}
