package com.hhdl.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class UriWrapTest {

    @Test
    public void createUri() {
        String url = "http://api.map.baidu.com/routematrix/v2/driving";
        Map param = new HashMap<>();
        param.put("output", "json");
        param.put("origins", "40.45,116.34|40.54,116.35|40.59,116.45|40.50,116.30");
        param.put("destinations", "40.34,116.45");
        param.put("ak", "122222222222222222222222");

        String uri = UriWrap.CreateUri(url, param);
        System.out.println(uri);
    }
}