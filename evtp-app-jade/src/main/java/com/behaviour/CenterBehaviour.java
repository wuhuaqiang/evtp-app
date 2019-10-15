package com.behaviour;

import com.agent.CenterAgent;
import com.domain.Task;
import com.util.Type;
import com.view.Screen;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.*;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Queue;

public class CenterBehaviour extends CyclicBehaviour{
	private static final long serialVersionUID = 1L;
	private CenterAgent agent;
	private HashMap<String,HashMap<String,Integer>> stationList;
	private HashMap<String,Queue<Task>> taskList;
	private Screen screen;
	private String dispatchMethod="sate";//type or sate
	public CenterBehaviour(CenterAgent centerAgent) {
		this.agent = centerAgent;
		taskList=centerAgent.getTaskList();
		stationList=centerAgent.getStationList();
		screen=centerAgent.getScreen();
	}

	@Override
	public void action() {		
		
		//定义两个消息模板接收INFORM和REQUEST消息
		MessageTemplate informTemplate=MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		MessageTemplate requestTemplate=MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		ACLMessage informMessage=agent.receive(informTemplate);
		ACLMessage requestMessage=agent.receive(requestTemplate);
		
		try {
			//收到station关于stationInfo的更新信息
			//在两种情况下会执行dispatchTaskByType()
			//1.收到stationInfo的更新，可能出现空闲设备；
			//2.收到新任务，此时可能有空闲设备；
			if(informMessage!=null){
				@SuppressWarnings("unchecked")
				HashMap<String,Integer> stationInfo=(HashMap<String, Integer>) informMessage.getContentObject();
				String stationName=informMessage.getSender().getLocalName();
				updateStaionList(stationName,stationInfo);
				if("type".equals(dispatchMethod)){
					dispatchTaskByType();
				}
				else{
					//dispatchMethod="sate"
					dispatchTaskBySate();
				}
				
			}
			//收到TaskGenerator生成的Task
			if(requestMessage!=null){
				Task task=(Task) requestMessage.getContentObject();
//				System.out.println(agent.getLocalName()+ " get "+task);
				if("type".equals(dispatchMethod)){
					putTaskToQueueByType(task);
					dispatchTaskByType();
				}
				else{
					//dispatchMethod="sate"
					putTaskToQueueBySate(task);
					dispatchTaskBySate();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//把任务按照类型加入相应的队列
	private void putTaskToQueueByType(Task task){
		String type=task.getType();
		HashMap<String,Queue<Task>> taskList=agent.getTaskList();
		Queue<Task> q=null;
		switch (type) {
		case Type.A:
			q=taskList.get("typeA");
			q.offer(task);
			break;
		case Type.B:
			q=taskList.get("typeB");
			q.offer(task);
			break;
		case Type.C:
			q=taskList.get("typeC");
			q.offer(task);
			break;
		case Type.D:
			q=taskList.get("typeD");
			q.offer(task);
			break;
	
		default:
			break;
		}
		showTaskListNum();
	}

	private void putTaskToQueueBySate(Task task){
	//		System.out.println("putTaskToQueueBySate(");
			String sateId=task.getSatelliteId();
	//		System.out.println("sateId="+sateId);
			HashMap<String,Queue<Task>> taskList=agent.getTaskList();
			Queue<Task> q=null;
			switch (sateId) {
			case "s1":
				q=taskList.get("station1");
				q.offer(task);
				break;
			case "s2":
				q=taskList.get("station4");
				q.offer(task);
				break;
			case "s3":
				q=taskList.get("station1");
				q.offer(task);
				break;
			case "s4":
				q=taskList.get("station2");
				q.offer(task);
				break;
			case "s5":
				q=taskList.get("station2");
				q.offer(task);
				break;
			case "s6":
				q=taskList.get("station3");
				q.offer(task);
				break;
			case "s7":
				q=taskList.get("station3");
				q.offer(task);
				break;
			case "s8":
				q=taskList.get("station4");
				q.offer(task);
				break;
	
			default:
				break;
			}
		}

	//显示各个类型的任务队列的数量
	private void showTaskListNum() {
		Queue<Task> q= taskList.get("typeA");
		System.out.println("typeA size is "+q.size());
		q= taskList.get("typeB");
		System.out.println("typeB size is "+q.size());
		q= taskList.get("typeC");
		System.out.println("typeC size is "+q.size());
		q= taskList.get("typeD");
		System.out.println("typeD size is "+q.size());
		
	}

	//显示各个类型的任务队列的数量
	private void showTaskListNumByStation() {
		Queue<Task> q= taskList.get("station1");
		System.out.println("station1 size is "+q.size());
		q= taskList.get("station2");
		System.out.println("station2 size is "+q.size());
		q= taskList.get("station3");
		System.out.println("station3 size is "+q.size());
		q= taskList.get("station4");
		System.out.println("station4 size is "+q.size());
	
		
	}

	private void dispatchTaskBySate() throws Exception {
		System.out.println("center dispatchTaskBySate()");
		showTaskListNumByStation();
		taskList=agent.getTaskList();
		for(Entry<String, Queue<Task>> entry:taskList.entrySet()){
			Queue<Task> q=entry.getValue();
			if(q.size()>0){
				//如果队列是非空的
				String stationName=entry.getKey();
				//先试探队列的第一个任务
				Task task=q.peek();
				if(isStationAvailable(stationName,task.getType())){
					task=q.poll();
					sendTaskToStation(task, stationName);
				}
			}
			
		}
	}

	//center轮询各个队列，若某个类型的设备空闲，则将那个设备所在的测站名称返回
	private void dispatchTaskByType() throws Exception {
//		showStationList();
		taskList=agent.getTaskList();
		Task task=null;
		String stationName=null;
		System.out.println("center dispatchTaskByType()");
		//如果有可用资源且任务队列中有任务
		if((stationName=isTypeAvailabe(Type.A))!=null&&
			taskList.get("typeA").size()>0){
			task=taskList.get("typeA").poll();	
			sendTaskToStation(task,stationName);
			stationName=null;
		}
		if((stationName=isTypeAvailabe(Type.B)) != null&&
			taskList.get("typeB").size()>0){
			task=taskList.get("typeB").poll();
			sendTaskToStation(task,stationName);
			stationName=null;
		}
		if((stationName=isTypeAvailabe(Type.C)) != null&&
			taskList.get("typeC").size()>0){
			task=taskList.get("typeC").poll();
			sendTaskToStation(task,stationName);
			stationName=null;
		}
		if((stationName=isTypeAvailabe(Type.D)) != null&&
			taskList.get("typeD").size()>0){
			task=taskList.get("typeD").poll();
			sendTaskToStation(task,stationName);
			stationName=null;
		}
		
	}
	
	//查询某一测站是否有可用的设备，若有则返回
	private boolean isStationAvailable(String stationName,String type){
		int count=agent.getStationList().get(stationName).get(type);
		if(count>0){
			return true;
		}
		return false;
	}
	
	//查询是否有某一类型的可用设备，如果有，返回设备所在测站，如果没有返回空值
	private String isTypeAvailabe(String type){
		stationList=agent.getStationList();
		String stationName=null;
		
		for(Entry<String, HashMap<String, Integer>> entry:stationList.entrySet()){
			stationName=entry.getKey();
			for(Entry<String,Integer> deviceInfo:entry.getValue().entrySet()){
				if(type.equals(deviceInfo.getKey())&&deviceInfo.getValue()>0)
					return stationName;
			}
		}
		return null;
	}
	
	
	//显示测站列表
//	private void showStationList() {
//		stationList=agent.getStationList();
//		for(Entry<String,HashMap<String,Integer>> entry:stationList.entrySet()){
//			System.out.print(entry.getKey()+":");
//			for(Entry<String,Integer> info:entry.getValue().entrySet()){
//				System.out.print(info.getKey()+"  "+info.getValue()+" ");
//			}
//			System.out.println("");
//		}
//	}

	//刷新stationList
	private void updateStaionList(String stationName, HashMap<String, Integer> stationInfo) {
		agent.getStationList().put(stationName, stationInfo);
	}

	//将任务发送给测站
	private void sendTaskToStation(Task task, String station) throws Exception {
		//刷新显示
		String content="center将任务"+task.getTaskId()+"分配给"+station+"\r\n";
		screen.updateCenterText(content);
		//将执行任务的测站名称加到任务，便于device返回结果
		task.setStationId(station);		
		AID rec=new AID(station+"@"+agent.getHap(), AID.ISGUID);
		ACLMessage message=new ACLMessage(ACLMessage.REQUEST);
		message.addReceiver(rec);
		message.setContentObject(task);
		agent.send(message);
	}
}
