package com.hhdl.model;

import java.io.Serializable;
import java.util.List;

public class TPointsLine implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private List<TPoint> tPoints;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<TPoint> gettPoints() {
        return tPoints;
    }

    public void settPoints(List<TPoint> tPoints) {
        this.tPoints = tPoints;
    }

    @Override
    public String toString() {
        return "TPointsLine{" +
                "userId='" + userId + '\'' +
                ", tPoints=" + tPoints +
                '}';
    }
}
