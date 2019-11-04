package com.hhdl.common.model;

public class Offer {
    private String userId;
    private Double pMax;
    private Double pMin;
    private Double soc;
    private String userRole;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getpMax() {
        return pMax;
    }

    public void setpMax(Double pMax) {
        this.pMax = pMax;
    }

    public Double getpMin() {
        return pMin;
    }

    public void setpMin(Double pMin) {
        this.pMin = pMin;
    }

    public Double getSoc() {
        return soc;
    }

    public void setSoc(Double soc) {
        this.soc = soc;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "userId='" + userId + '\'' +
                ", pMax=" + pMax +
                ", pMin=" + pMin +
                ", soc=" + soc +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
