package com.behaviour;

import com.agent.TaskGenerator;
import com.domain.Task;
import com.view.Screen;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class GenerateTask extends Behaviour {
	private static final long serialVersionUID = 1L;
	private TaskGenerator agent;
	private int index=1;
	private Screen screen;
	public GenerateTask(TaskGenerator taskGenerator) {
		agent=taskGenerator;
		this.screen=taskGenerator.getScreen();
	}

	@Override
	public void action() {
		int random=(int) (Math.random()*100+1);		
		System.out.println("random="+random);
		try {
			if(random>10){
				generateTask(random);
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void generateTask(int random) throws Exception {
		Task task= Task.createTask("t"+index, random);
		sendTaskToCenter(task);		
		System.out.println(task);
		String newTaskText=task.getTaskId()+" ";
		newTaskText+="卫星:"+task.getSatelliteId()+" ";
		newTaskText+="类型:"+task.getType()+" ";
		newTaskText+="总时间:"+task.getTotalTime()+"\r\n";
		//刷新显示
		screen.updateNewTask(newTaskText);
		index++;
	}

	private void sendTaskToCenter(Task task) throws Exception {
//		System.out.println("generator send task to center begin----");
		AID center=new AID("center@"+agent.getHap(), AID.ISGUID);
		ACLMessage message=new ACLMessage(ACLMessage.REQUEST);
		message.addReceiver(center);
		message.setContentObject(task);
		agent.send(message);
//		System.out.println("----generator send task to center end");
		
	}

	@Override
	public boolean done() {
		
		return index==21;
	}

}
