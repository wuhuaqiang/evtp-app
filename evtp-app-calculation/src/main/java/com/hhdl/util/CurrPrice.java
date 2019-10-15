package com.hhdl.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrPrice {
    private static final String xStr = "0,1,1,2,2,3,3,4,4,5,5,6,6,7,7,7.2,7.2,8,8,9,9,10,10,11,11,12,12,13,13,14,14,15,15,16,16,17,17,18,18,19,19,20,20,21,21,22,22,23,23,24";
    private static final String yStr = "0.25,0.25,0.21,0.21,0.17,0.17,0.06,0.06,0.06,0.06,0.19,0.19,0.38,0.38,0.46,0.46,0.49,0.49,0.55,0.55,0.56,0.56,0.53,0.53,0.52,0.52,0.51,0.51,0.52,0.52,0.52,0.52,0.48,0.48,0.5,0.5,0.59,0.59,0.69,0.69,0.62,0.62,0.57,0.57,0.49,0.49,0.5,0.5,0.51,0.51";
    private static final Map<String, String> basePrice = new HashMap();
    private static final List<String> xStrList = new ArrayList();
    private static final List<String> yStrList = new ArrayList();

    public static void main(String[] args) {
        String[] xStrs = xStr.split(",");
        String[] yStrs = yStr.split(",");
        for (int i = 0; i < xStrs.length; i++) {
            basePrice.put(xStrs[i], yStrs[i]);
        }
        System.out.println(basePrice);
        getCurrPrice(3.9);
    }

    private static String getCurrPrice(double time) {
        int time1 = (int) time;
//            System.out.println(time1);
//            System.out.println(basePrice.get(time1 + ""));
        getHisPrice(time1);
        if (time % 1 != 0) {
            xStrList.add(time+"");
            yStrList.add(basePrice.get(time1 + ""));
        }
        System.out.println(xStrList);
        System.out.println(yStrList);
        return "ok";
    }

    private static String getHisPrice(int time) {
        xStrList.add(0 + "");
        yStrList.add(basePrice.get(0 + ""));
        yStrList.add(basePrice.get(0 + ""));
        for (int i = 1; i <= time; i++) {
            xStrList.add(i + "");
            xStrList.add(i + "");
            if (i != time) {
                yStrList.add(basePrice.get(i + ""));
                yStrList.add(basePrice.get(i + ""));
            } else {
                yStrList.add(basePrice.get(i + ""));
            }
        }
        return "ok";
    }

}
