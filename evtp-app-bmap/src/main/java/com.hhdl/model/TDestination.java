package com.hhdl.model;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-03-19
 */
@TableName("t_destination")
public class TDestination implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String lat;
    private String lng;


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

    @Override
    public String toString() {
        return "TDestination{" +
        "id=" + id +
        ", lat=" + lat +
        ", lng=" + lng +
        "}";
    }
}
