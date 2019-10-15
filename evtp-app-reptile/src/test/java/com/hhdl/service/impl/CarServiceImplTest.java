package com.hhdl.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.common.util.UUIDKey;
import com.hhdl.model.TElectricVehicleInfo;
import com.hhdl.service.ApiService;
import com.hhdl.service.TElectricVehicleInfoService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceImplTest {
    @Autowired
    private ApiService apiService;
    @Autowired
    private CarServiceImpl carServiceImpl;
    @Autowired
    private TElectricVehicleInfoService tElectricVehicleInfoService;
    public final static String SCRIPT_PRE = "var rules = '';var document = {};document.createElement = function() {return {sheet: {insertRule: " +
            "function(rule, i) {if (rules.length == 0) {rules = rule;} else {rules = rules + '|' + rule;}}}}};document.getElementsByTagName = " +
            "function() {};document.querySelectorAll = function() {return {};};document.head = {};document.head.appendChild = function() " +
            "{};var window = {};window.decodeURIComponent = decodeURIComponent;window.location = {};window.location.href = 'car.m.autohome.com.cn';";

    public final static Pattern CSS_PATTERN = Pattern.compile("\\.(.*)::before.*content:\"(.*)\".*");

    @Test
    public void testGet() throws Exception {
//        String url = "https://car.autohome.com.cn/config/series/692.html";
        //最后的数据，解析json就ok

//        for (int i = 1; i < 157; i++) {
//            String url = "https://www.autohome.com.cn/bestauto/" + i;
//            String html = apiService.getHtml(url);
//            Document domcument = Jsoup.parse(html);
//            Elements divs = domcument.select("#bestautocontent div.uibox");
//            for (Element div : divs) {
//                //去重过滤，重复的数据不需要再处理
//                String title = div.select("div.uibox-title").first().text();
//                //页面
//                Map<String, String> carTest = carServiceImpl.getCarTest(div);
//                //图片
//                Thread.sleep(1000);
//                String Image = carServiceImpl.getCarImage(div);
//
//                System.out.println(Image);
//                System.out.println(carTest.toString());
//            }
//        }
        String carTypePre = "";
        String carTypeEnd = "";
        String chargingTimePre = "";
        String chargingTimeEnd = "";
        for (int i = 0; i < 10000; i++) {
            //http://car.bitauto.com/gaojixuanche/?f=16,128&page=6#anchorTarget
//            String url = "http://select.car.yiche.com/selectcartool/searchresult?f=16,128&page=" + i + "&pagesize=50";
            String url = "http://select.car.yiche.com/selectcartool/searchresult?page=" + i + "&pagesize=50";
            String html = apiService.getHtml(url);
            if (html == null) {
                return;
            }
            JSONObject jsonObject = (JSONObject) JSON.parse(html);
            String carNumber = jsonObject.getString("CarNumber");
            String count = jsonObject.getString("Count");
            JSONArray resList = jsonObject.getJSONArray("ResList");
            if (resList.size() == 0) {
                return;
            }
            System.out.println("carNumber:" + carNumber + "-----count:" + count);
            for (Object object : resList) {
                JSONObject obj = (JSONObject) object;
                String carNum = obj.getString("CarNum");
                String carIdList = obj.getString("CarIdList");
                String heatRank = obj.getString("HeatRank");
                String allSpell = obj.getString("AllSpell");
                String imageUrl = obj.getString("ImageUrl");
                String masterId = obj.getString("MasterId");
                String serialId = obj.getString("SerialId");
                String showName = obj.getString("ShowName");
                String dealerCount = obj.getString("DealerCount");
                String pVNum = obj.getString("PVNum");
                String priceRange = obj.getString("PriceRange");
                System.out.println("allSpell:" + allSpell);
                String carUrl = "http://car.bitauto.com/" + allSpell + "/peizhi/";
                /*String carUrl = "http://car.bitauto.com/" + allSpell + "/peizhi/";
                String carPeizhiHtml = apiService.getHtml(carUrl);
                Document domcument = Jsoup.parse(carPeizhiHtml);*/
                Connection.Response response = Jsoup.connect(carUrl).validateTLSCertificates(false).ignoreContentType(true).ignoreHttpErrors(true).execute();
                System.out.println(response.statusCode());
                Document document = response.parse();
                Elements scripts = document.select("script:containsData(insertRule)");

                ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
                ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
                Map<String, String> cssKeyValue = new HashMap<>();
                System.out.println("------------css数据------------");
                scripts.forEach(element -> {
                    String script = SCRIPT_PRE + element.html();
                    try {
                        engine.eval(script);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }
                    String css = (String) engine.get("rules");
                    System.out.println(css);
                    for (String str : css.split("\\|")) {
                        Matcher cssMatcher = CSS_PATTERN.matcher(str);
                        if (cssMatcher.find()) {
                            cssKeyValue.put("<span class='" + cssMatcher.group(1) + "'></span>", cssMatcher.group(2));
                        }
                    }
                });
                Elements contents = document.select("script:containsData(keyLink)");
                Elements contentjs = document.select("script:containsData(carCompareJson)");
                String content = contents.html();
                String contentj = contentjs.html();
                System.out.println("------------用css混淆的配置数据------------");
                System.out.println(content);
                System.out.println(contentj);
                //把混淆数据里的样式用上面解析的样式给替代
                for (Map.Entry<String, String> entry : cssKeyValue.entrySet()) {
                    content = content.replaceAll(entry.getKey(), entry.getValue());
                }
                System.out.println("------------用css替换后的数据------------");
                System.out.println(content);
                engine.eval(content);
                engine.eval(contentj);
                System.out.println("------------每个配置结果------------");
                String keyLink = JSONObject.toJSONString(engine.get("keyLink"));
                String config = JSONObject.toJSONString(engine.get("config"));
                String option = JSONObject.toJSONString(engine.get("option"));
                String bag = JSONObject.toJSONString(engine.get("bag"));
                String color = JSONObject.toJSONString(engine.get("color"));
                String innerColor = JSONObject.toJSONString(engine.get("innerColor"));
                String carCompareJson = JSONObject.toJSONString(engine.get("carCompareJson"));
                System.out.println(keyLink);
                System.out.println(config);
                System.out.println(option);
                System.out.println(bag);
                System.out.println(color);
                System.out.println(innerColor);
                JSONObject carJson = JSONObject.parseObject(carCompareJson);
                Map<String, Object> carMap = JSONObject.toJavaObject(carJson, Map.class);

                for (Map.Entry<String, Object> ret1Entry : carMap.entrySet()) {
                    String key1 = ret1Entry.getKey();
                    System.out.println(key1);
                    Map<String, Object> ret1 = JSONObject.toJavaObject((JSONObject) ret1Entry.getValue(), Map.class);
                    TElectricVehicleInfo tElectricVehicleInfo = new TElectricVehicleInfo();
                    for (Map.Entry<String, Object> ret2Entry : ret1.entrySet()) {
                        String key2 = ret2Entry.getKey();
                        System.out.println(key2);
                        Map<String, Object> ret2 = JSONObject.toJavaObject((JSONObject) ret2Entry.getValue(), Map.class);
                        for (Map.Entry<String, Object> ret3Entry : ret2.entrySet()) {

                            String key3 = ret3Entry.getKey();
                            String value = (String) ret3Entry.getValue();
                            if ("0".equals(key2) && "1".equals(key3)) {
                                carTypeEnd = value;
                            }
                            if ("0".equals(key2) && "7".equals(key3)) {
                                carTypePre = value + "款";
                            }
                            if ("3".equals(key2) && "28".equals(key3)) {
                                if (value != null && !"".equals(value) && !"0".equals(value)) {
                                    chargingTimePre = "快充" + value + "h";
                                }

                            }
                            if ("3".equals(key2) && "27".equals(key3)) {
                                if (value != null && !"".equals(value) && !"0".equals(value)) {
                                    chargingTimeEnd = "慢充" + value + "h";
                                }
                            }
                            if ("1".equals(key2) && "0".equals(key3)) {
                                tElectricVehicleInfo.setPrice(value);
                            }
                            if ("1".equals(key2) && "14".equals(key3)) {
                                tElectricVehicleInfo.setDynamicType(value);
                            }
                            if ("2".equals(key2) && "4".equals(key3)) {
//                                if (value == null && "".equals(value) || "0".equals(value)) {
//                                    tElectricVehicleInfo.setQuality("");
//                                } else {
//                                    tElectricVehicleInfo.setQuality(value + "kg");
//                                }
                                tElectricVehicleInfo.setQuality(value);

                            }
                            if ("3".equals(key2) && "26".equals(key3)) {
//                                tElectricVehicleInfo.setBatteryCapacity(value + "[kwh]");
                                tElectricVehicleInfo.setBatteryCapacity(value);
                            }
                            if ("3".equals(key2) && "29".equals(key3)) {
//                                tElectricVehicleInfo.setPowerConsumption(value + "[kwh/100km]");
                                tElectricVehicleInfo.setPowerConsumption(value);
                            }
                            if ("3".equals(key2) && "30".equals(key3)) {
//                                tElectricVehicleInfo.setMaximumRange(value + "[km]");
                                tElectricVehicleInfo.setMaximumRange(value);
                            }
                        }
                    }
                    tElectricVehicleInfo.setId(UUIDKey.getKey());
                    tElectricVehicleInfo.setCarName(showName);
                    tElectricVehicleInfo.setChargingTime(chargingTimePre + chargingTimeEnd);
                    tElectricVehicleInfo.setCarType(carTypePre + carTypeEnd);
                    tElectricVehicleInfo.setCreated(new Date());
                    Wrapper<TElectricVehicleInfo> electricVehicleInfoWrapper = new EntityWrapper<>();
                    electricVehicleInfoWrapper.where("car_name={0}", tElectricVehicleInfo.getCarName()).and("car_type={0}", tElectricVehicleInfo.getCarType());
                    List<TElectricVehicleInfo> tElectricVehicleInfos = tElectricVehicleInfoService.selectList(electricVehicleInfoWrapper);
                    if (tElectricVehicleInfos != null && tElectricVehicleInfos.size() > 0) {
                        tElectricVehicleInfo.setId(tElectricVehicleInfos.get(0).getId());
                        tElectricVehicleInfo.setUpdated(new Date());
                    }
                    tElectricVehicleInfoService.insertOrUpdate(tElectricVehicleInfo);
                }

                System.out.println(carMap);
//                TElectricVehicleInfo tElectricVehicleInfo = new TElectricVehicleInfo();
//                tElectricVehicleInfo.setId(UUIDKey.getKey());
//                tElectricVehicleInfo.setCarName(showName);
//                tElectricVehicleInfoService.insertOrUpdate(tElectricVehicleInfo);
            }

        }
      /*  String url = "https://price.pcauto.com.cn/sg20982/config.html";
        String html = apiService.getHtml(url);
        Document domcument = Jsoup.parse(html);
        Elements table = domcument.select("table");*/
//        for (int i = 1; i < 157; i++) {
//            String url = "http://car.bitauto.com/gaojixuanche/?f=16&page=" + i + "#anchorTarget";
//            String html = apiService.getHtml(url);
//            Document domcument = Jsoup.parse(html);
//            Elements divs = domcument.select("#divContent div.col-auto");
//            for (Element div : divs) {
//
//                String carUrl = div.select("li.name a").attr("href");
//                System.out.println(carUrl);
//
//            }
//
//        }

    }
}