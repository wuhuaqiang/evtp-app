package com.hhdl.controller.rest;

import com.hhdl.common.model.CommonResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tElectricityPrice")
public class TElectricityPrice {
    private static final String xStr = "0,1,1,2,2,3,3,4,4,5,5,6,6,7,7,7.2,7.2,8,8,9,9,10,10,11,11,12,12,13,13,14,14,15,15,16,16,17,17,18,18,19,19,20,20,21,21,22,22,23,23,24";
    private static final String yStr = "0.25,0.25,0.21,0.21,0.17,0.17,0.06,0.06,0.06,0.06,0.19,0.19,0.38,0.38,0.46,0.46,0.49,0.49,0.55,0.55,0.56,0.56,0.53,0.53,0.52,0.52,0.51,0.51,0.52,0.52,0.52,0.52,0.48,0.48,0.5,0.5,0.59,0.59,0.69,0.69,0.62,0.62,0.57,0.57,0.49,0.49,0.5,0.5,0.51,0.51";
    private static final Map<String, String> basePrice = new HashMap();
    private static final List<String> xStrList = new ArrayList();
    private static final List<String> yStrList = new ArrayList();

    static {
        String[] xStrs = xStr.split(",");
        String[] yStrs = yStr.split(",");
        for (int i = 0; i < xStrs.length; i++) {
            basePrice.put(xStrs[i], yStrs[i]);
        }
    }

    @RequestMapping("/getPrices")
    public CommonResult getPrices(@RequestParam(value = "time") String time) {
        yStrList.clear();
        Map result = new HashMap();
        Integer num = Integer.valueOf(time);
        yStrList.add(basePrice.get("0"));
        yStrList.add(basePrice.get("0"));
        for (int i = 1; i <= num; i++) {
                yStrList.add(basePrice.get(i + ""));
        }
        result.put("yStr", yStrList);
        return CommonResult.success(result);
    }
    @RequestMapping("/getPricesbf")
    public CommonResult getPricesbf(@RequestParam(value = "time") String time) {
        xStrList.clear();
        yStrList.clear();
        Map result = new HashMap();
        Double aDouble = Double.valueOf(time);
        int time1 = (int) aDouble.doubleValue();
        getHisPrice(time1);
        if (aDouble % 1 != 0) {
            xStrList.add(time);
            yStrList.add(basePrice.get(time1 + ""));
        }
        result.put("xStr", xStrList);
        result.put("yStr", yStrList);
        return CommonResult.success(result);
    }


    private void getHisPrice(int time) {
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
    }
}
