package com.agent;

import com.behaviour.StationBehaviour;
import com.util.DeviceInfo;
import com.view.Screen;
import jade.core.Agent;

import java.util.HashMap;

public class StationAgent extends Agent{
	private static final long serialVersionUID = 1L;
	private HashMap<String,Integer> stationInfo;
	private HashMap<String, DeviceInfo> deviceList;
	private Screen screen;
	public HashMap<String, Integer> getStationInfo() {
		return stationInfo;
	}
	public void setStationInfo(HashMap<String, Integer> stationInfo) {
		this.stationInfo = stationInfo;
	}
	public HashMap<String, DeviceInfo> getDeviceList() {
		return deviceList;   
	}
	public void setDeviceList(HashMap<String, DeviceInfo> deviceList) {
		this.deviceList = deviceList;
	}
	@Override
	protected void setup() {
		addBehaviour(new StationBehaviour(this));
	}
	public Screen getScreen() {
		return screen;
	}
	public void setScreen(Screen screen) {
		this.screen = screen;
	}
}
