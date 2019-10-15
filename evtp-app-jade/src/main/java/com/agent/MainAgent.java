package com.agent;

import com.behaviour.MainBehaviour;
import jade.core.Agent;

public class MainAgent extends Agent{
	private static final long serialVersionUID = 1L;
	@Override
	protected void setup() {
		this.addBehaviour(new MainBehaviour());
	}
}
