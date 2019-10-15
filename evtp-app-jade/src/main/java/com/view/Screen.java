package com.view;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Screen extends JFrame{
	private static final long serialVersionUID = 1L;
	//主面，左面，右面
	private JPanel mainPanel,leftPanel,rightPanel;
	//左面，新任务
	private JScrollPane newTaskPane;
	private JTextArea newTaskArea;
	//左面，center
	private JScrollPane centerPane;
	private JTextArea centerArea;
	//左面，时间
	private JPanel timePanel;
	private JTextArea startTimeText,currentTimeText,totalTimeText;
	//左面，测站信息
	private JPanel stationInfoPanel;
	private JTextArea station1InfoText,station2InfoText,station3InfoText,station4InfoText;
	//右面，各个测站任务
	private JTextArea station1Task,station2Task,station3Task,station4Task;
	private JScrollPane station1TaskPane,station2TaskPane,station3TaskPane,station4TaskPane;		
	//屏幕参数
	private int screenWidth;
	private int screenHeight;
	//刷新时间组件
	private Date startDate;
	private SimpleDateFormat format;
	private Timer timer;
	private boolean finish=false;
	//字体
	private Font f1;
	public Screen(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		new SimpleDateFormat("hh:mm:ss");
		screenWidth=(int) screenSize.getWidth();
		screenHeight=(int) screenSize.getHeight();
		f1=new Font("宋体", Font.PLAIN, 17);
		init();
		timer=new Timer();
        timer.schedule(new RemindTask(), 0, 10000);
	}

	public void closeTimer(){
		if(timer!=null){
			timer.cancel();
		}
	}

	private void init() {
		drawPanel();
		setFrame();
		
	}
	private void drawPanel() {
		//左面，新任务
		newTaskArea=new JTextArea();
		newTaskArea.setText("");
		newTaskArea.setFont(f1);
		newTaskPane=new JScrollPane(newTaskArea);
		newTaskPane.setBorder(
				BorderFactory.createTitledBorder ("生成任务"));
		
		//左面，center
		centerArea=new JTextArea("");
		centerArea.setFont(f1);
		centerPane=new JScrollPane(centerArea);
		centerPane.setBorder(
				BorderFactory.createTitledBorder ("center"));
				
		//左面，时间
		timePanel=new JPanel(new GridLayout(3,1));
		timePanel.setBorder(BorderFactory.createTitledBorder("时间"));
		startDate=new Date();
		startTimeText=new JTextArea("开始时间   "+format.format(startDate));
		startTimeText.setFont(f1);
		currentTimeText=new JTextArea("当前时间   "+format.format(startDate));
		currentTimeText.setFont(f1);
		totalTimeText=new JTextArea("一共用时   0s");
		totalTimeText.setFont(f1);
		timePanel.add(startTimeText);
		timePanel.add(currentTimeText);
		timePanel.add(totalTimeText);

		//显示测站信息
		stationInfoPanel=new JPanel(new GridLayout(4,1));
		stationInfoPanel.setBorder(BorderFactory.createTitledBorder("各个测站可用资源"));
		
		station1InfoText=new JTextArea("station1:A设备2台，B设备2台");
		station1InfoText.setFont(f1);
		
		station2InfoText=new JTextArea("station2:B设备2台，C设备2台");
		station2InfoText.setFont(f1);
		
		station3InfoText=new JTextArea("station3:C设备2台，D设备2台");
		station3InfoText.setFont(f1);
		
		station4InfoText=new JTextArea("station4:A设备2台，D设备2台");
		station4InfoText.setFont(f1);
		
		stationInfoPanel.add(station1InfoText);
		stationInfoPanel.add(station2InfoText);
		stationInfoPanel.add(station3InfoText);
		stationInfoPanel.add(station4InfoText);
		
		//左边显示新任务、中心分配任务情况、时间、资源
		leftPanel=new JPanel(new GridLayout(4,1,10,10));
		leftPanel.setBounds(10, 10, 300, 540);
		leftPanel.add(newTaskPane);
		leftPanel.add(centerPane);
		leftPanel.add(timePanel);
		leftPanel.add(stationInfoPanel);
		
		//显示各个测站及设备任务运行情况
		station1Task=new JTextArea();				
		station1Task.setText("");
		station1Task.setFont(f1);
		station1TaskPane=new JScrollPane(station1Task);
		station1TaskPane.setBorder(
			BorderFactory.createTitledBorder ("station1"));
		
		station2Task=new JTextArea();
		station2Task.setText("");
		station2Task.setFont(f1);
		station2TaskPane=new JScrollPane(station2Task);
		station2TaskPane.setBorder(
			BorderFactory.createTitledBorder ("station2"));
		
		station3Task=new JTextArea();
		station3Task.setText("");
		station3Task.setFont(f1);
		station3TaskPane=new JScrollPane(station3Task);
		station3TaskPane.setBorder(
			BorderFactory.createTitledBorder ("station3"));
		
		station4Task=new JTextArea();
		station4Task.setText("");
		station4Task.setFont(f1);
		station4TaskPane=new JScrollPane(station4Task);
		station4TaskPane.setBorder(
			BorderFactory.createTitledBorder ("station4"));
		//右边，显示任务运行情况 790-320
		rightPanel=new JPanel(new GridLayout(4,1,10,10));
		rightPanel.setBounds(320, 10, 465, 540);
		rightPanel.add(station1TaskPane);
		rightPanel.add(station2TaskPane);
		rightPanel.add(station3TaskPane);
		rightPanel.add(station4TaskPane);
				
		mainPanel=new JPanel(null);
		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);
	}
	private void setFrame(){
		this.setTitle("TTC System");
		this.setContentPane(mainPanel);
		this.setLocation(screenWidth/2-400,screenHeight/2-300);
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	//显示新生成的任务
	public void updateNewTask(String newTask){
		newTaskArea.append(newTask);
	}
	//更新右边任务栏，参数是测站名称和内容
	public void updateStationTask(String stationName,String content){
//		System.out.println("screen updateStationTask "+stationName+" "+content);
		if(content.contains("完成")&&content.contains("t20")){
			finish=true;
		}
			
		switch (stationName) {
		case "station1":
			station1Task.append(content);
			break;
		case "station2":
			station2Task.append(content);
			break;
		case "station3":
			station3Task.append(content);
			break;
		case "station4":
			station4Task.append(content);
			break;

		default:
			break;
		}
		if(finish){
			closeTimer();
		}
	}
	
	//更新测站信息
	public void updateStationList(String stationName,HashMap<String,Integer> stationInfo){
		String content="";
		switch (stationName) {
		case "station1":
			content="station1 "+"A设备"+stationInfo.get("A")+"台，";
			content+="B设备"+stationInfo.get("B")+"台"+"\r\n";
			station1InfoText.setText(content);
			break;
		case "station2":
			content="station2 "+"B设备"+stationInfo.get("B")+"台，";
			content+="C设备"+stationInfo.get("C")+"台"+"\r\n";
			station2InfoText.setText(content);
			break;
		case "station3":
			content="station3 "+"C设备"+stationInfo.get("C")+"台，";
			content+="D设备"+stationInfo.get("D")+"台"+"\r\n";
			station3InfoText.setText(content);
			break;
		case "station4":
			content="station4 "+"A设备"+stationInfo.get("A")+"台，";
			content+="D设备"+stationInfo.get("D")+"台"+"\r\n";
			station4InfoText.setText(content);
			break;

		default:
			break;
		}
	}
	
	public void updateCenterText(String content){
		centerArea.append(content);
	}
	
	private class RemindTask extends TimerTask{

		@Override
		public void run() {
			Date currentDate=new Date();
			long totalTime=currentDate.getTime()/1000-startDate.getTime()/1000;
			currentTimeText.setText("当前时间   "+format.format(currentDate));
			long hour=totalTime/3600;
			long min=(totalTime-3600*hour)/60;
			long second=totalTime%3600;
			totalTimeText.setText("一共用时   "+hour+":"+min+":"+second);
		}
		
	}

//	public static void main(String[] args) {
//		new Screen();
//	}
}
