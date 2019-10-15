package com.hhdl.controller.rest;

import com.hhdl.common.model.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.*;

@RestController
@RequestMapping("/tCurrElectricityPrice")
public class TElectricityPriceController {
    private static final Map<String, String> basePrice = new HashMap();
    private static final Map<String, Integer> marks = new HashMap();
    private static final List<String> xStrList = new ArrayList();
    private static final List<String> yStrList = new ArrayList();

    static {
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 60; j++) {
                String h = "";
                String m = "";
                if (i < 10) {
                    h = "0" + i;
                } else {
                    h = h + i;
                }
                if (j < 10) {
                    m = "0" + j;
                } else {
                    m = m + j;
                }
                String currPrice = getRandomNumber(0.2, 1.0) + "";
                String currTime = h + ":" + m;
                xStrList.add(currTime);
                yStrList.add(currPrice);
                basePrice.put(currTime, currPrice);
            }

        }
        for (int i = 0; i < xStrList.size(); i++) {
            marks.put(xStrList.get(i), i);
        }
        System.out.println(basePrice);
    }

    @RequestMapping("/getPrices")
    public CommonResult getPrices(@RequestParam(value = "time") String time) {
        Integer integer = marks.get(time);
        Map<String, List> result = new HashMap();
        List<String> prices = new ArrayList();
        for (int i = 0; i <= integer; i++) {
            prices.add(yStrList.get(i));
        }
        result.put("xStr",xStrList);
        result.put("yStr",prices);
        return CommonResult.success(result);
    }

    /**
     * 获取一定范围的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static double getRandomNumber(final double min, final double max) { //保留两位小数
        DecimalFormat df = new DecimalFormat("#.00");
        if (min == max) {
            return Double.parseDouble(df.format(min));
        }
        return Double.parseDouble(df.format(min + ((max - min) * new Random().nextDouble())));
    }
}
