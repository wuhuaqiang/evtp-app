package com.hhdl.entity;

import com.alibaba.fastjson.JSON;
import com.hhdl.endpoint.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

@Component
public class WebPointsTask extends TimerTask {
    @Autowired
    private WebSocket webSocket;
    private Queue<Map> mapQueue = new LinkedBlockingDeque<>();

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public Queue<Map> getMapQueue() {
        return mapQueue;
    }

    public void setMapQueue(Queue<Map> mapQueue) {
        this.mapQueue = mapQueue;
    }

    @Override
    public void run() {
        int num = mapQueue.size();
        List<Map> mapList = new ArrayList<>();
        try {
            for (int i = 0; i < num; i++) {
                mapList.add(mapQueue.remove());
            }
            if (mapList.size() > 0) {
                webSocket.sendInfo(JSON.toJSONString(mapList));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
