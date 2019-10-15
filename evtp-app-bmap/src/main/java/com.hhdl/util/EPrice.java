package com.hhdl.util;

import java.util.*;

public class EPrice {
    private static final int sumTime = 24 * 60;
    private static final List currList = new ArrayList();

    public static void main(String[] args) {
        List currPrices = getCurrPrices(15);
        System.out.println(currPrices);
    }

    public static List getCurrPrices(int setp) {
        ;
        Random r = new Random();
        int num = sumTime / setp;
        int last = sumTime % setp;
        int x = 0;
        double lastP = 0.0;
        if (last != 0) {
            num += 1;
        }
        for (int i = 0; i < num; i++) {
            if (i == 0) {
                lastP = r.nextDouble();
                x = i * setp;
                addPoint(x, lastP);
            } else {
                x = i * setp;
                addPoint(x, lastP);
                lastP = r.nextDouble();
                addPoint(x, lastP);
            }
        }
        addPoint(sumTime, lastP);
        return currList;
    }

    public static void addPoint(int x, double y) {
        Map map = new HashMap();
        map.put(x, y);
        currList.add(map);
    }
}
