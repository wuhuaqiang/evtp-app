package com.hhdl.model;

import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-08-01
 */
@TableName("evtp_points")
public class EvtpPoints implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String address;
    private String lat;
    private String lng;
    private String title;
    private Integer type;
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "EvtpPoints{" +
        "id=" + id +
        ", address=" + address +
        ", lat=" + lat +
        ", lng=" + lng +
        ", title=" + title +
        ", type=" + type +
        ", remark=" + remark +
        "}";
    }
}
