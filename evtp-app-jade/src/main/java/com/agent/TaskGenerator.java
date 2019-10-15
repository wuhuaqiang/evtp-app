package com.agent;

import com.behaviour.GenerateTask;
import com.view.Screen;
import jade.core.Agent;

public class TaskGenerator extends Agent{
	private static final long serialVersionUID = 1L;
	private Screen screen;
	@Override
	protected void setup() {
		addBehaviour(new GenerateTask(this));
	}
	public Screen getScreen() {
		return screen;
	}
	public void setScreen(Screen screen) {
		this.screen = screen;
	}
}
