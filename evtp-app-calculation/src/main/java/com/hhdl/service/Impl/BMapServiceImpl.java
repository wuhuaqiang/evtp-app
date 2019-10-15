package com.hhdl.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.dao.TChargingStationDao;
import com.hhdl.dao.TElectricVehicleDao;
import com.hhdl.enums.BaiDuApi;
import com.hhdl.model.TChargingStation;
import com.hhdl.model.TElectricVehicle;
import com.hhdl.service.BMapService;
import com.hhdl.util.StringUtil;
import com.hhdl.util.UriWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getNearestChargingStation(String carId) throws Exception {
        TElectricVehicle tElectricVehicle = tElectricVehicleDao.selectById(carId);
        String[] split1 = tElectricVehicle.getPositionVal().split(",");
        String origin = split1[1] + "," + split1[0];
        Wrapper<TChargingStation> tChargingStationWrapper = new EntityWrapper<TChargingStation>();
        List<TChargingStation> tChargingStations = tChargingStationDao.selectList(tChargingStationWrapper);
        String destination = "";
        List<String> tChargingStationIds = new ArrayList<String>();
        for (TChargingStation tChargingStation : tChargingStations) {
            tChargingStationIds.add(tChargingStation.getId());
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
        param.put("ak", BaiDuApi.BAIDU_API_KEY);
        String uri = UriWrap.CreateUri(BaiDuApi.BAIDU_API_DRIVING, param);
        String ret = restTemplate.getForObject(uri, String.class, param);
//        HttpClientResult httpClientResult = HttpClientUtils.doGet("", param);
//        JSONObject jsonObject = JSONObject.parseObject(httpClientResult.getContent());
        JSONObject jsonObject = JSONObject.parseObject(ret);
        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(jsonObject);
        JSONArray result = (JSONArray) map.get("result");
        String minValue = "";
        Map<String, String> resultMap = new HashMap<String, String>();
        for (int i = 0; i < result.size(); i++) {
            JSONObject jsonObject1 = (JSONObject) result.get(i);
            JSONObject distance = jsonObject1.getJSONObject("distance");
            String value = distance.getString("value");
            System.out.println(value);
            if (StringUtil.isEmpty(minValue)) {
                minValue = value;
                resultMap.put("minDistance",minValue);
                resultMap.put("catID",tChargingStationIds.get(i));
            } else {
                if (Integer.valueOf(minValue) > Integer.valueOf(value)) {
                    minValue = value;
                    resultMap.put("minDistance",minValue);
                    resultMap.put("catID",tChargingStationIds.get(i));
                }
            }

            System.out.println(value);
        }

        System.out.println(result);
        System.err.println(minValue);
        System.err.println(resultMap);
        return "123";
    }

    @Override
    public Boolean ifgoNearestChargingStation(String carId) {
        return null;
    }
}
