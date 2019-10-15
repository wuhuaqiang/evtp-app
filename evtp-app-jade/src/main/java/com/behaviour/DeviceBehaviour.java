package com.behaviour;

import com.agent.DeviceAgent;
import com.domain.Task;
import com.util.DeviceInfo;
import com.util.Status;
import com.view.Screen;
import jade.core.*;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DeviceBehaviour extends CyclicBehaviour {
	private static final long serialVersionUID = 1L;
	private DeviceAgent agent;
	private Screen screen;
	public DeviceBehaviour(DeviceAgent deviceAgent) {
		agent=deviceAgent;
		screen=deviceAgent.getScreen();
	}

	@Override
	public void action() {
		
		MessageTemplate reqeustTemplate=
			MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		ACLMessage reqeustMessage=agent.receive(reqeustTemplate);
		try {
			if(reqeustMessage!=null){
				Task task=(Task)reqeustMessage.getContentObject();
				executeTask(task);
			}
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
	}

	private void executeTask(Task task) throws Exception {
//		System.out.println(agent.getLocalName()+" get task="+task);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式 
		String begin=format.format(new Date())+" 设备"+
			agent.getLocalName()+"开始执行任务"+task.getTaskId()+"\r\n";
//		System.out.println(agent.getLocalName()+" begin:"+begin);
		screen.updateStationTask(task.getStationId(), begin);
//		System.out.println("Begin:"+format.format(new Date()));// new Date()
		agent.setStatus(Status.BUSY);
		int totalTime=task.getTotalTime();
		reportToStation(task);
		try {
			for(int i=0;i<totalTime;i++){
				Thread.sleep(1000);
//				System.out.println("remain="+(totalTime-i));
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		agent.setStatus(Status.FREE);
		reportToStation(task);
		String end=format.format(new Date())+" 设备"+
			agent.getLocalName()+"完成任务"+task.getTaskId()+",用时"+
			task.getTotalTime()+"s"+"\r\n";
		screen.updateStationTask(task.getStationId(), end);
//		System.out.println("End:"+format.format(new Date()));// new Date()
	}

	//在执行任务task前后汇报自身状态
	private void reportToStation(Task task) throws Exception {
		String stationName=task.getStationId();
		AID rec=new AID(stationName+"@"+agent.getHap(), AID.ISGUID);
		ACLMessage message=new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(rec);
		DeviceInfo deviceInfo=new DeviceInfo();
		deviceInfo.setType(agent.getType());
		deviceInfo.setStatus(agent.getStatus());
		message.setContentObject(deviceInfo);
		agent.send(message);
//		System.out.println("reportToStation report="+agent.getLocalName()+" "+deviceInfo);
	}
	

}
