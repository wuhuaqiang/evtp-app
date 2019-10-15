package com.hhdl.model;

import com.hhdl.util.Point;

import java.util.Queue;

public class RunTask {
    private String taskId;
    private String carId;
    private String name;
    private String userId;
    private Queue<MapPoint> content;
    private MapPoint mapPoint;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Queue<MapPoint> getContent() {
        return content;
    }

    public void setContent(Queue<MapPoint> content) {
        this.content = content;
    }

    public MapPoint getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
    }

    @Override
    public String toString() {
        return "RunTask{" +
                "taskId='" + taskId + '\'' +
                ", carId='" + carId + '\'' +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", content=" + content +
                ", mapPoint=" + mapPoint +
                '}';
    }
}
