package com.hhdl.evtp.controller.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhdl.evtp.service.ChainCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by linwf on 2018/11/3.
 */
@Controller
@RequestMapping("/chaincode")
public class ChainCodeController {
    @Resource
    private ChainCodeService chainCodeService;

    /**
     * 安装智能合约
     * @return
     */
    @RequestMapping(value = "/install", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String chainCodeInstall() {
        return chainCodeService.chainCodeInstall();
    }

    /**
     * 实例化智能合约
     * @param map
     * @return
     */
    @RequestMapping(value = "/instantiate", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String chainCodeInstantiate(@RequestBody Map<String, Object> map) {
        JSONObject json = new JSONObject(map);
        JSONArray arrayJson = json.getJSONArray("args");
        int length = arrayJson.size();
        String[] argArray = new String[length];
        for (int i = 0; i < length; i++) {
            argArray[i] = arrayJson.getString(i);
        }
        return chainCodeService.chainCodeInstantiate(argArray);
    }

    /**
     * 升级智能合约
     * @param map
     * @return
     */
    @RequestMapping(value = "/upgrade", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String chainCodeUpgrade(@RequestBody Map<String, Object> map) {
        JSONObject json = new JSONObject(map);
        JSONArray arrayJson = json.getJSONArray("args");
        int length = arrayJson.size();
        String[] argArray = new String[length];
        for (int i = 0; i < length; i++) {
            argArray[i] = arrayJson.getString(i);
        }
        return chainCodeService.chainCodeUpgrade(argArray);
    }

    /**
     * 执行智能合约
     * @param map
     * @return
     */
    @RequestMapping(value = "/invoke", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String chainCodeInvoke(@RequestBody Map<String, Object> map) {
        JSONObject json = new JSONObject(map);
        String fcn = json.getString("fcn");
        JSONArray arrayJson = json.getJSONArray("args");
        int length = arrayJson.size();
        String[] argArray = new String[length];
        for (int i = 0; i < length; i++) {
            argArray[i] = arrayJson.getString(i);
        }
        return chainCodeService.chainCodeInvoke(fcn, argArray);
    }
    /**
     * 执行智能合约
     * @param map
     * @return
     */
    @RequestMapping(value = "/invokeBatch", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String chainCodeInvokeBatch(@RequestBody Map<String, Object> map) {
        JSONObject json = new JSONObject(map);
        String fcn = json.getString("fcn");
        JSONArray arrayJson = json.getJSONArray("args");
        int length = arrayJson.size();
        for (int j = 0; j < length; j++){
            JSONArray jsonArray = JSON.parseArray(arrayJson.getString(j));
            int size = jsonArray.size();
            String[] argArray = new String[size];
            for (int i = 0; i < size; i++) {
                argArray[i] = jsonArray.getString(i);
            }
            chainCodeService.chainCodeInvoke(fcn, argArray);
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }


        return "执行成功！";
    }
    /**
     * 查询智能合约
     * @param map
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String chainCodeQuery(@RequestBody Map<String, Object> map) {
        JSONObject json = new JSONObject(map);
        String fcn = json.getString("fcn");
        JSONArray arrayJson = json.getJSONArray("args");
        int length = arrayJson.size();
        String[] argArray = new String[length];
        for (int i = 0; i < length; i++) {
            argArray[i] = arrayJson.getString(i);
        }
        return chainCodeService.chainCodeQuery(fcn, argArray);
    }


}
