package com.hhdl.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.dao.TChargingStationDao;
import com.hhdl.dao.TElectricVehicleDao;
import com.hhdl.entity.HttpClientResult;
import com.hhdl.model.MapPoint;
import com.hhdl.model.TChargingStation;
import com.hhdl.model.TElectricVehicle;
import com.hhdl.service.BMapService;
import com.hhdl.util.HttpClientUtils;
import com.hhdl.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BMapServiceImpl implements BMapService {
    @Autowired
    private TElectricVehicleDao tElectricVehicleDao;
    @Autowired
    private TChargingStationDao tChargingStationDao;

    @Override
    public String getNearestChargingStation(String carId) throws Exception {
        TElectricVehicle tElectricVehicle = tElectricVehicleDao.selectById(carId);
        String[] split1 = tElectricVehicle.getPositionVal().split(",");
        String origin = split1[1] + "," + split1[0];
        Wrapper<TChargingStation> tChargingStationWrapper = new EntityWrapper<TChargingStation>();
        List<TChargingStation> tChargingStations = tChargingStationDao.selectList(tChargingStationWrapper);
        String destination = "";
        for (TChargingStation tChargingStation : tChargingStations) {
            String[] split = tChargingStation.getPositionVal().split(",");
            destination += split[1] + "," + split[0] + "|";

        }
        String destinations = destination.substring(0, destination.length() - 1);
        System.out.println(destination);
        System.out.println(origin);
        Map param = new HashMap();
        param.put("output", "json");
        param.put("origins", origin);
        param.put("destinations", destinations);
        HttpClientResult httpClientResult = HttpClientUtils.doGet("http://api.map.baidu.com/routematrix/v2/driving", param);
        JSONObject jsonObject = JSONObject.parseObject(httpClientResult.getContent());
        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(jsonObject);
        JSONArray result = (JSONArray) map.get("result");
        String minValue = "";
        for (int i = 0; i < result.size(); i++) {
            JSONObject jsonObject1 = (JSONObject) result.get(i);
            JSONObject distance = jsonObject1.getJSONObject("distance");
            String value = distance.getString("value");
            System.out.println(value);
            if (StringUtil.isEmpty(minValue)) {
                minValue = value;
            } else {
                if (Integer.valueOf(minValue) > Integer.valueOf(value)) {
                    minValue = value;
                }
            }
            System.out.println(value);
        }

        System.out.println(result);
        System.err.println(minValue);
        return "123";
    }

    @Override
    public Boolean ifgoNearestChargingStation(String carId) {
        return null;
    }

    @Override
    public List<MapPoint> getLinePoints(String origin, String destination) throws Exception {
        origin = getLatLngStr(origin);
        destination = getLatLngStr(destination);
        List<MapPoint> mapPoints = new ArrayList<>();
        Map param = new HashMap();
        HttpClientResult httpClientResults = HttpClientUtils.doGet("http://api.map.baidu.com/directionlite/v1/driving?origin=40.01116,116.339303&destination=39.936404,116.452562");
        JSONObject jsonObjects = JSONObject.parseObject(httpClientResults.getContent());
        param.put("output", "json");
        param.put("origin", origin);
        param.put("destination", destination);
        HttpClientResult httpClientResult = HttpClientUtils.doGet("http://api.map.baidu.com/directionlite/v1/driving", param);
        JSONObject jsonObject = JSONObject.parseObject(httpClientResult.getContent());
        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(jsonObject);
        JSONObject result = (JSONObject) map.get("result");
        JSONArray routes = (JSONArray) result.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        JSONArray steps = (JSONArray) route.get("steps");
        for (int i = 0; i < steps.size(); i++) {
            JSONObject jsonObject1 = (JSONObject) steps.get(i);
            String path = jsonObject1.get("path").toString();
            String[] mapPointStrs = path.split(";");
            for (String mapPointStr : mapPointStrs) {
                String[] mapPointArr = mapPointStr.split(",");
                MapPoint mapPoint = new MapPoint();
                mapPoint.setLat(Double.valueOf(mapPointArr[1]));
                mapPoint.setLng(Double.valueOf(mapPointArr[0]));
                mapPoints.add(mapPoint);
                System.out.println(mapPoint);
            }
        }
        return mapPoints;
    }

    private String getLatLngStr(String oldLngLat) {
        String[] oldLngLatArr = oldLngLat.split(",");
        String oldLng = oldLngLatArr[0];
        String oldLat = oldLngLatArr[1];
        if(oldLat.length()>9){
            oldLat = oldLat.substring(0,9);
        }
        if(oldLng.length()>10){
            oldLng = oldLng.substring(0,9);
        }
        return  oldLat + "," + oldLng;
    }

//    private Boolean checkLngLatStr(String oldLngLat) {
//        String[] split = oldLngLat.split(",");
//        String[] split1 = split[0].split(".");
//        String[] split2 = split[1].split(".");
//        return split1[1].length() == 6 && split2[1].length() == 6;
//    }
}
