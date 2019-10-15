package com.behaviour;

import com.agent.StationAgent;
import com.domain.Task;
import com.util.DeviceInfo;
import com.util.Status;
import com.view.Screen;
import jade.core.*;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.*;

import java.util.HashMap;
import java.util.Map.Entry;

public class StationBehaviour extends CyclicBehaviour {
	private static final long serialVersionUID = 1L;
	private StationAgent agent;
	private Screen screen;
	public StationBehaviour(StationAgent stationAgent) {
		this.screen=stationAgent.getScreen();
		agent=stationAgent;
	}

	@Override
	public void action() {

		//定义两个消息模板接收INFORM和REQUEST消息
		MessageTemplate informTemplate=MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		MessageTemplate requestTemplate=MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		ACLMessage informMessage=agent.receive(informTemplate);
		ACLMessage requestMessage=agent.receive(requestTemplate);
		
		try {	
			if(requestMessage!=null){
				//如果收到center来的request，分派任务给device
				Task task=(Task)requestMessage.getContentObject();
//				System.out.println("station "+agent.getLocalName()+" get task "+task);
				String deviceName=selectDevice(task.getType());
				//刷新显示
				String content=agent.getLocalName()+"将任务"+
					task.getTaskId()+"分配给测站"+deviceName+"\r\n";
				screen.updateStationTask(agent.getLocalName(), content);
				dispatchToDevice(deviceName,task);
			}
			if(informMessage!=null){			
				//如果收到来自device的汇报信息，则修改deviceList和stationInfo
				//然后向center汇报自身信息变化
				DeviceInfo deviceInfo=(DeviceInfo)informMessage.getContentObject();
				String deviceName=informMessage.getSender().getLocalName();
//				System.out.println("get report from:"+deviceName+" "+deviceInfo);
				//更新设备列表
				updateInfo(deviceName,deviceInfo);
				//向中心汇报stationInfo变化
				reportToCenter();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//向中心汇报自身stationInfo的变化
	private void reportToCenter() throws Exception {
		AID center=new AID("center"+"@"+agent.getHap(), AID.ISGUID);
		ACLMessage message=new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(center);
		message.setContentObject(agent.getStationInfo());
		agent.send(message);
	}
	
	//选择合适的device
	private String selectDevice(String type) {
		String deviceName=null;
		HashMap<String, DeviceInfo> deviceList=agent.getDeviceList();
		for(Entry<String, DeviceInfo> entry:deviceList.entrySet()){
			DeviceInfo info=entry.getValue();
			if(type.equals(info.getType())&&info.getStatus().equals(Status.FREE)){
				deviceName=entry.getKey();
				return deviceName;
			}
		}
		if(deviceName==null){
			System.out.println(agent.getLocalName()+" select no device!");
		}
		return deviceName;
	}

	//更新deviceList和stationInfo
	private void updateInfo(String deviceName, DeviceInfo deviceInfo) {
		//更新设备列表
		agent.getDeviceList().put(deviceName, deviceInfo);
		//更新stationInfo
		String status=deviceInfo.getStatus();
		String type=deviceInfo.getType();
		int count=agent.getStationInfo().get(type);
		if(Status.FREE.equals(status))
			count+=1;
		if(Status.BUSY.equals(status))
			count-=1;
		agent.getStationInfo().put(type, count);
		screen.updateStationList(agent.getLocalName(), agent.getStationInfo());
	}

	//将任务转发至所选择的device
	private void dispatchToDevice(String deviceName, Task task) throws Exception {
		AID device=new AID(deviceName+"@"+agent.getHap(),AID.ISGUID);
		ACLMessage message=new ACLMessage(ACLMessage.REQUEST);		
		message.addReceiver(device);
		message.setContentObject(task);
		agent.send(message);    				
	}

}
