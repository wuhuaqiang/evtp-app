package com.domain;

import com.util.Type;

import java.io.Serializable;

public class Task implements Serializable {
	private static final long serialVersionUID = 1L;
	private String taskId;
	private String satelliteId;
	private String type;
	private int totalTime;
	private int remainTime;
	private String stationId;
	
	public static Task createTask(String id, int random){
		Task task=new Task();
		task.setTaskId(id);
		String satellite=new String();
		String type=new String();
		int sat=random%8;
		switch (sat) {
		case 0:
			satellite="s1";
			type=Type.A;
			break;
		case 1:
			satellite="s2";
			type=Type.A;
			break;
		case 2:
			satellite="s3";
			type=Type.B;
			break;
		case 3:
			satellite="s4";
			type=Type.B;
			break;
		case 4:
			satellite="s5";
			type=Type.C;
			break;
		case 5:
			satellite="s6";
			type=Type.C;
			break;
		case 6:
			satellite="s7";
			type=Type.D;
			break;
		case 7:
			satellite="s8";
			type=Type.D;
			break;
		default:
			break;
		}
		
		task.setSatelliteId(satellite);
		task.setType(type);
		task.setStationId("null");
		int time=(int) (Math.random()*20)+1;
		task.setTotalTime(time);
		task.setRemainTime(time);
		return task;
	}
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getSatelliteId() {
		return satelliteId;
	}
	public void setSatelliteId(String satelliteId) {
		this.satelliteId = satelliteId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	public int getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", satelliteId=" + satelliteId
				+ ", type=" + type + ", totalTime=" + totalTime
				+ ", remainTime=" + remainTime + ", stationId=" + stationId
				+ "]";
	}
	
	public static void main(String[] args) {
		System.out.println(Task.createTask("t1", 21));
	}
}
