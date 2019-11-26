package com.hhdl.evtp.fabric.sdkinterface;

import java.util.Map;


public interface EventListener {
    void received(Map<String, String> map);
}
