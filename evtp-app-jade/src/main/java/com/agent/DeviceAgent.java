package com.agent;

import com.behaviour.DeviceBehaviour;
import com.view.Screen;
import jade.core.Agent;

public class DeviceAgent extends Agent{
	private static final long serialVersionUID = 1L;
	private String status;
	private String type;
	private Screen screen;
	@Override
	protected void setup() {
		addBehaviour(new DeviceBehaviour(this));
	}

	public String getStatus() {
		return status;   
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	
}
