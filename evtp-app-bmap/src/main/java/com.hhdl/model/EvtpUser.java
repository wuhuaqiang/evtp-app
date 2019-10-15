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
 * @since 2019-08-05
 */
@TableName("evtp_user")
public class EvtpUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("ev_id")
    private String evId;
    private String name;
    @TableField("home_position_id")
    private String homePositionId;
    @TableField("company_position_id")
    private String companyPositionId;
    @TableField("other_position_id")
    private String otherPositionId;
    private String sex;
    private String email;
    private String address;
    private String phone;
    private String qq;
    private String telnumber;
    private String createdate;
    private String state;
    private String remark;
    private String account;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomePositionId() {
        return homePositionId;
    }

    public void setHomePositionId(String homePositionId) {
        this.homePositionId = homePositionId;
    }

    public String getCompanyPositionId() {
        return companyPositionId;
    }

    public void setCompanyPositionId(String companyPositionId) {
        this.companyPositionId = companyPositionId;
    }

    public String getOtherPositionId() {
        return otherPositionId;
    }

    public void setOtherPositionId(String otherPositionId) {
        this.otherPositionId = otherPositionId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public void setTelnumber(String telnumber) {
        this.telnumber = telnumber;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "EvtpUser{" +
        "id=" + id +
        ", evId=" + evId +
        ", name=" + name +
        ", homePositionId=" + homePositionId +
        ", companyPositionId=" + companyPositionId +
        ", otherPositionId=" + otherPositionId +
        ", sex=" + sex +
        ", email=" + email +
        ", address=" + address +
        ", phone=" + phone +
        ", qq=" + qq +
        ", telnumber=" + telnumber +
        ", createdate=" + createdate +
        ", state=" + state +
        ", remark=" + remark +
        ", account=" + account +
        "}";
    }
}
