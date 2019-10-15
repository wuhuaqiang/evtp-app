package com.agent;

import com.behaviour.CenterBehaviour;
import com.domain.Task;
import com.view.Screen;
import jade.core.Agent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class CenterAgent extends Agent{
	private static final long serialVersionUID = 1L;
	//各个测站信息列表
	private HashMap<String,HashMap<String,Integer>> stationList;
	private HashMap<String,Queue<Task>> taskList;
	private Screen screen;
	@Override
	protected void setup() {
		setParameter();
		addBehaviour(new CenterBehaviour(this));
	}
	private void setParameter() {
		buildStationList();
		buildTaskList();									
	}
	private void buildTaskList() {
		Queue<Task> station1=new LinkedList<Task>();
		Queue<Task> station2=new LinkedList<Task>();
		Queue<Task> station3=new LinkedList<Task>();
		Queue<Task> station4=new LinkedList<Task>();
		Queue<Task> typeA=new LinkedList<Task>();
		Queue<Task> typeB=new LinkedList<Task>();
		Queue<Task> typeC=new LinkedList<Task>();
		Queue<Task> typeD=new LinkedList<Task>();
		taskList=new HashMap<String, Queue<Task>>();
		taskList.put("station1", station1);
		taskList.put("station2", station2);
		taskList.put("station3", station3);
		taskList.put("station4", station4);
		taskList.put("typeA", typeA);
		taskList.put("typeB", typeB);
		taskList.put("typeC", typeC);
		taskList.put("typeD", typeD);
		
	}
	private void buildStationList() {
		stationList=new HashMap<String, HashMap<String,Integer>>();
		HashMap<String,Integer> stationInfo1=new HashMap<String,Integer>();
		stationInfo1.put("A", 2);
		stationInfo1.put("B", 2);
		
		HashMap<String,Integer> stationInfo2=new HashMap<String,Integer>();
		stationInfo2.put("B", 2);
		stationInfo2.put("C", 2);
		
		
		HashMap<String,Integer> stationInfo3=new HashMap<String,Integer>();
		stationInfo3.put("C", 2);
		stationInfo3.put("D", 2);
		
		HashMap<String,Integer> stationInfo4=new HashMap<String,Integer>();
		stationInfo4.put("A", 2);
		stationInfo4.put("D", 2);
		
		stationList.put("station1", stationInfo1);
		stationList.put("station2", stationInfo2);
		stationList.put("station3", stationInfo3);
		stationList.put("station4", stationInfo4);
		
	}
	public HashMap<String,HashMap<String,Integer>> getStationList() {
		return stationList;
	}
	public void setStationList(HashMap<String,HashMap<String,Integer>> stationList) {
		this.stationList = stationList;
	}
	public HashMap<String,Queue<Task>> getTaskList() {
		return taskList;
	}
	public void setTaskList(HashMap<String,Queue<Task>> taskList) {
		this.taskList = taskList;
	}
	public Screen getScreen() {
		return screen;
	}
	public void setScreen(Screen screen) {
		this.screen = screen;
	}
}
