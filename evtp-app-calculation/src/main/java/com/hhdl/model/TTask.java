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
@TableName("t_task")
public class TTask extends Model<TTask> {

    private static final long serialVersionUID = 1L;

    private String id;
    private String point;
    private Long time;
    private Integer sort;
    private String state;
    @TableField("ower_id")
    private String owerId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOwerId() {
        return owerId;
    }

    public void setOwerId(String owerId) {
        this.owerId = owerId;
    }

    @Override
    public String toString() {
        return "TTask{" +
                "id=" + id +
                ", point=" + point +
                ", time=" + time +
                ", sort=" + sort +
                ", state=" + state +
                ", owerId=" + owerId +
                "}";
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
