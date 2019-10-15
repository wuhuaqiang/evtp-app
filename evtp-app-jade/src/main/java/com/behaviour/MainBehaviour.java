package com.behaviour;

import com.agent.CenterAgent;
import com.agent.DeviceAgent;
import com.agent.StationAgent;
import com.agent.TaskGenerator;
import com.util.DeviceInfo;
import com.util.Status;
import com.util.Type;
import com.view.Screen;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.ContainerController;

import java.util.HashMap;

public class MainBehaviour extends OneShotBehaviour {
	private static final long serialVersionUID = 1L;
	private Screen screen;
	//一个中心代理
	private CenterAgent center;
	//四个测站代理
	private StationAgent station1;
	private StationAgent station2;
	private StationAgent station3;
	private StationAgent station4;
	//16个设备代理
	//station1的四个设备代理
	private DeviceAgent d1a1;
	private DeviceAgent d1a2;
	private DeviceAgent d1b1;
	private DeviceAgent d1b2;
	//station2的四个设备代理
	private DeviceAgent d2b1;
	private DeviceAgent d2b2;
	private DeviceAgent d2c1;
	private DeviceAgent d2c2;
	//station3的四个设备代理
	private DeviceAgent d3c1;
	private DeviceAgent d3c2;
	private DeviceAgent d3d1;
	private DeviceAgent d3d2;
	//station4的四个设备代理
	private DeviceAgent d4a1;
	private DeviceAgent d4a2;
	private DeviceAgent d4d1;
	private DeviceAgent d4d2;
	private TaskGenerator generator;
	@Override
	public void action() {
		System.out.println("Main action");
		screen=new Screen();
		createAgents();
		setAgents();
		startAgents();
	}

	private void startAgents() {
		ContainerController controller=myAgent.getContainerController();
		try {
			controller.acceptNewAgent("center", center);
			controller.getAgent("center").start();
			
			controller.acceptNewAgent("station1", station1);
			controller.getAgent("station1").start();
			
			controller.acceptNewAgent("station2", station2);
			controller.getAgent("station2").start();
			
			controller.acceptNewAgent("station3", station3);
			controller.getAgent("station3").start();
			
			controller.acceptNewAgent("station4", station4);
			controller.getAgent("station4").start();
			
			controller.acceptNewAgent("d1a1", d1a1);
			controller.getAgent("d1a1").start();
			controller.acceptNewAgent("d1a2", d1a2);
			controller.getAgent("d1a2").start();
			controller.acceptNewAgent("d1b1", d1b1);
			controller.getAgent("d1b1").start();
			controller.acceptNewAgent("d1b2", d1b2);
			controller.getAgent("d1b2").start();
			
			controller.acceptNewAgent("d2b1", d2b1);
			controller.getAgent("d2b1").start();
			controller.acceptNewAgent("d2b2", d2b2);
			controller.getAgent("d2b2").start();
			controller.acceptNewAgent("d2c1", d2c1);
			controller.getAgent("d2c1").start();
			controller.acceptNewAgent("d2c2", d2c2);
			controller.getAgent("d2c2").start();
			
			controller.acceptNewAgent("d3c1", d3c1);
			controller.getAgent("d3c1").start();
			controller.acceptNewAgent("d3c2", d3c2);
			controller.getAgent("d3c2").start();
			controller.acceptNewAgent("d3d1", d3d1);
			controller.getAgent("d3d1").start();
			controller.acceptNewAgent("d3d2", d3d2);
			controller.getAgent("d3d2").start();
			
			controller.acceptNewAgent("d4a1", d4a1);
			controller.getAgent("d4a1").start();
			controller.acceptNewAgent("d4a2", d4a2);
			controller.getAgent("d4a2").start();
			controller.acceptNewAgent("d4d1", d4d1);
			controller.getAgent("d4d1").start();
			controller.acceptNewAgent("d4d2", d4d2);
			controller.getAgent("d4d2").start();
			
			controller.acceptNewAgent("generator", generator);
			controller.getAgent("generator").start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setAgents() {
		//设置DeviceAgent类型
		setDeviceAgentsType();
		//设置DeviceAgent状态
		setDeviceAgentsStatus();
		//设置StationAgent的设备列表
		setStationAgentsDeviceList();
		//设置stationAgent的stationInfo属性
		setStationAgentStationInfo();
	}



	private void setStationAgentStationInfo() {
		HashMap<String,Integer> stationInfo1=new HashMap<String,Integer>();
		stationInfo1.put("A", 2);
		stationInfo1.put("B", 2);
		station1.setStationInfo(stationInfo1);
		
		HashMap<String,Integer> stationInfo2=new HashMap<String,Integer>();
		stationInfo2.put("B", 2);
		stationInfo2.put("C", 2);
		station2.setStationInfo(stationInfo2);
		
		HashMap<String,Integer> stationInfo3=new HashMap<String,Integer>();
		stationInfo3.put("C", 2);
		stationInfo3.put("D", 2);
		station3.setStationInfo(stationInfo3);
		
		HashMap<String,Integer> stationInfo4=new HashMap<String,Integer>();
		stationInfo4.put("A", 2);
		stationInfo4.put("D", 2);
		station4.setStationInfo(stationInfo4);
		
	}

	//设置StationAgent的设备列表
	private void setStationAgentsDeviceList() {
		HashMap<String, DeviceInfo> deviceList1=new HashMap<String, DeviceInfo>();
		DeviceInfo d1a1Info=new DeviceInfo();
		d1a1Info.setType(Type.A);
		d1a1Info.setStatus(Status.FREE);
		deviceList1.put("d1a1", d1a1Info);
		
		DeviceInfo d1a2Info=new DeviceInfo();
		d1a2Info.setType(Type.A);
		d1a2Info.setStatus(Status.FREE);
		deviceList1.put("d1a2", d1a2Info);
		
		DeviceInfo d1b1Info=new DeviceInfo();
		d1b1Info.setType(Type.B);
		d1b1Info.setStatus(Status.FREE);
		deviceList1.put("d1b1", d1b1Info);
		
		DeviceInfo d1b2Info=new DeviceInfo();
		d1b2Info.setType(Type.B);
		d1b2Info.setStatus(Status.FREE);
		deviceList1.put("d1b2", d1b2Info);
		
		station1.setDeviceList(deviceList1);
		
		HashMap<String, DeviceInfo> deviceList2=new HashMap<String, DeviceInfo>();
		DeviceInfo d2b1Info=new DeviceInfo();
		d2b1Info.setType(Type.B);
		d2b1Info.setStatus(Status.FREE);
		deviceList2.put("d2b1", d2b1Info);
		
		DeviceInfo d2b2Info=new DeviceInfo();
		d2b2Info.setType(Type.B);
		d2b2Info.setStatus(Status.FREE);
		deviceList2.put("d2b2", d2b2Info);
		
		DeviceInfo d2c1Info=new DeviceInfo();
		d2c1Info.setType(Type.C);
		d2c1Info.setStatus(Status.FREE);
		deviceList2.put("d2c1", d2c1Info);
		
		DeviceInfo d2c2Info=new DeviceInfo();
		d2c2Info.setType(Type.C);
		d2c2Info.setStatus(Status.FREE);
		deviceList2.put("d2c2", d2c2Info);
		
		station2.setDeviceList(deviceList2);
		
		HashMap<String, DeviceInfo> deviceList3=new HashMap<String, DeviceInfo>();
		DeviceInfo d3c1Info=new DeviceInfo();
		d3c1Info.setType(Type.C);
		d3c1Info.setStatus(Status.FREE);
		deviceList3.put("d3c1", d3c1Info);
		
		DeviceInfo d3c2Info=new DeviceInfo();
		d3c2Info.setType(Type.C);
		d3c2Info.setStatus(Status.FREE);
		deviceList3.put("d3c2", d3c2Info);
		
		DeviceInfo d3d1Info=new DeviceInfo();
		d3d1Info.setType(Type.D);
		d3d1Info.setStatus(Status.FREE);
		deviceList3.put("d3d1", d3d1Info);
		
		DeviceInfo d3d2Info=new DeviceInfo();
		d3d2Info.setType(Type.D);
		d3d2Info.setStatus(Status.FREE);
		deviceList3.put("d3d2", d3d2Info);
		
		station3.setDeviceList(deviceList3);
		
		HashMap<String, DeviceInfo> deviceList4=new HashMap<String, DeviceInfo>();
		DeviceInfo d4a1Info=new DeviceInfo();
		d4a1Info.setType(Type.A);
		d4a1Info.setStatus(Status.FREE);
		deviceList4.put("d4a1", d4a1Info);
		
		DeviceInfo d4a2Info=new DeviceInfo();
		d4a2Info.setType(Type.A);
		d4a2Info.setStatus(Status.FREE);
		deviceList4.put("d4a2", d4a2Info);
		
		DeviceInfo d4d1Info=new DeviceInfo();
		d4d1Info.setType(Type.D);
		d4d1Info.setStatus(Status.FREE);
		deviceList4.put("d4d1", d4a1Info);
		
		DeviceInfo d4d2Info=new DeviceInfo();
		d4d2Info.setType(Type.D);
		d4d2Info.setStatus(Status.FREE);
		deviceList4.put("d4d2", d4d2Info);
		
		station4.setDeviceList(deviceList4);
	}

	//设置DeviceAgent状态
	private void setDeviceAgentsStatus() {
		d1a1.setStatus(Status.FREE);
		d1a2.setStatus(Status.FREE);
		d1b1.setStatus(Status.FREE);
		d1b2.setStatus(Status.FREE);
		
		d2b1.setStatus(Status.FREE);
		d2b2.setStatus(Status.FREE);
		d2c1.setStatus(Status.FREE);
		d2c2.setStatus(Status.FREE);
		
		d3c1.setStatus(Status.FREE);
		d3c2.setStatus(Status.FREE);
		d3d1.setStatus(Status.FREE);
		d3d2.setStatus(Status.FREE);
		
		d4a1.setStatus(Status.FREE);
		d4a2.setStatus(Status.FREE);
		d4d1.setStatus(Status.FREE);
		d4d2.setStatus(Status.FREE);
		
	}
	
	//设置DeviceAgent类型
	private void setDeviceAgentsType() {
		d1a1.setType(Type.A);
		d1a2.setType(Type.A);
		d4a1.setType(Type.A);
		d4a2.setType(Type.A);
		
		d1b1.setType(Type.B);
		d1b2.setType(Type.B);
		d2b1.setType(Type.B);
		d2b2.setType(Type.B);
		
		d2c1.setType(Type.C);
		d2c2.setType(Type.C);
		d3c1.setType(Type.C);
		d3c2.setType(Type.C);
		
		d3d1.setType(Type.D);
		d3d2.setType(Type.D);
		d4d1.setType(Type.D);
		d4d2.setType(Type.D);
		
	}

	private void createAgents() {
		center=new CenterAgent();
		center.setScreen(screen);
		station1=new StationAgent();
		station1.setScreen(screen);
		d1a1=new DeviceAgent();
		d1a2=new DeviceAgent();
		d1b1=new DeviceAgent();
		d1b2=new DeviceAgent();
		d1a1.setScreen(screen);
		d1a2.setScreen(screen);
		d1b1.setScreen(screen);
		d1b2.setScreen(screen);
		
		station2=new StationAgent();
		station2.setScreen(screen);
		d2b1=new DeviceAgent();
		d2b2=new DeviceAgent();
		d2c1=new DeviceAgent();
		d2c2=new DeviceAgent();
		d2b1.setScreen(screen);
		d2b2.setScreen(screen);
		d2c1.setScreen(screen);
		d2c2.setScreen(screen);
		
		station3=new StationAgent();
		station3.setScreen(screen);
		d3c1=new DeviceAgent();
		d3c2=new DeviceAgent();
		d3d1=new DeviceAgent();
		d3d2=new DeviceAgent();
		d3c1.setScreen(screen);
		d3c2.setScreen(screen);
		d3d1.setScreen(screen);
		d3d2.setScreen(screen);
		
		station4=new StationAgent();
		station4.setScreen(screen);
		d4a1=new DeviceAgent();
		d4a2=new DeviceAgent();
		d4d1=new DeviceAgent();
		d4d2=new DeviceAgent();
		d4a1.setScreen(screen);
		d4a2.setScreen(screen);
		d4d1.setScreen(screen);
		d4d2.setScreen(screen);
		
		generator=new TaskGenerator();
		generator.setScreen(screen);
		
	}

}
