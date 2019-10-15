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

		//����������Ϣģ�����INFORM��REQUEST��Ϣ
		MessageTemplate informTemplate=MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		MessageTemplate requestTemplate=MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		ACLMessage informMessage=agent.receive(informTemplate);
		ACLMessage requestMessage=agent.receive(requestTemplate);
		
		try {	
			if(requestMessage!=null){
				//����յ�center����request�����������device
				Task task=(Task)requestMessage.getContentObject();
//				System.out.println("station "+agent.getLocalName()+" get task "+task);
				String deviceName=selectDevice(task.getType());
				//ˢ����ʾ
				String content=agent.getLocalName()+"������"+
					task.getTaskId()+"�������վ"+deviceName+"\r\n";
				screen.updateStationTask(agent.getLocalName(), content);
				dispatchToDevice(deviceName,task);
			}
			if(informMessage!=null){			
				//����յ�����device�Ļ㱨��Ϣ�����޸�deviceList��stationInfo
				//Ȼ����center�㱨������Ϣ�仯
				DeviceInfo deviceInfo=(DeviceInfo)informMessage.getContentObject();
				String deviceName=informMessage.getSender().getLocalName();
//				System.out.println("get report from:"+deviceName+" "+deviceInfo);
				//�����豸�б�
				updateInfo(deviceName,deviceInfo);
				//�����Ļ㱨stationInfo�仯
				reportToCenter();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//�����Ļ㱨����stationInfo�ı仯
	private void reportToCenter() throws Exception {
		AID center=new AID("center"+"@"+agent.getHap(), AID.ISGUID);
		ACLMessage message=new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(center);
		message.setContentObject(agent.getStationInfo());
		agent.send(message);
	}
	
	//ѡ����ʵ�device
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

	//����deviceList��stationInfo
	private void updateInfo(String deviceName, DeviceInfo deviceInfo) {
		//�����豸�б�
		agent.getDeviceList().put(deviceName, deviceInfo);
		//����stationInfo
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

	//������ת������ѡ���device
	private void dispatchToDevice(String deviceName, Task task) throws Exception {
		AID device=new AID(deviceName+"@"+agent.getHap(),AID.ISGUID);
		ACLMessage message=new ACLMessage(ACLMessage.REQUEST);		
		message.addReceiver(device);
		message.setContentObject(task);
		agent.send(message);    				
	}

}
