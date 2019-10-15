package com;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class StartMainAgent {
    public static void main(String[] asgs) {
        try {
            Runtime rt = Runtime.instance();
            rt.setCloseVM(true);
            Profile pMain = new ProfileImpl("172.20.0.1", 8888, null);
            System.out.println("Launching a whole in-process platform..." + pMain);
            AgentContainer mc = rt.createMainContainer(pMain);
            // set now the default Profile to start a container
            ProfileImpl pContainer = new ProfileImpl("172.20.0.1", 8888, null);
            System.out.println("Launching the agent container ..." + pContainer);
            AgentController custom = mc.createNewAgent("mainAgent", "com.agent.MainAgent", null);
            custom.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
