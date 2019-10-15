package com.hhdl.model;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2019-03-18
 */
@TableName("t_point")
public class TPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String lat;
    private String lag;


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

    public String getLag() {
        return lag;
    }

    public void setLag(String lag) {
        this.lag = lag;
    }

    @Override
    public String toString() {
        return "TPoint{" +
                "id=" + id +
                ", lat=" + lat +
                ", lag=" + lag +
                "}";
    }
}
