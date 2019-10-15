package com.hhdl.evtp.service.Impl;

import com.hhdl.evtp.dao.FabricConfigMapper;
import com.hhdl.evtp.sdk.SdkManager;
import com.hhdl.evtp.service.ChainCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by linwf on 2018/11/3.
 */
@Service("chainCodeService")
public class ChainCodeServiceImpl extends BaseServiceImpl implements ChainCodeService {

    @Autowired
    private FabricConfigMapper fabricConfigMapper;

    @Override
    public String chainCodeInstall() {
        SdkManager manager = SdkManager.getInstance(fabricConfigMapper);
        Map<String, String> resultMap;
        try {
            resultMap = manager.chainCodeInstall();
            if (resultMap.get("code").equals("error")) {
                return super.responseFail(resultMap.get("data"));
            } else {
                return super.responseSuccess(resultMap.get("data"), resultMap.get("txid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return super.responseFail(String.format("请求失败:%s", e.getMessage()));
        }
    }

    @Override
    public String chainCodeInstantiate(String[] args) {
        SdkManager manager = SdkManager.getInstance(fabricConfigMapper);
        Map<String, String> resultMap;
        try {
            resultMap = manager.chainCodeInstantiate(args);
            if (resultMap.get("code").equals("error")) {
                return super.responseFail(resultMap.get("data"));
            } else {
                return super.responseSuccess(resultMap.get("data"), resultMap.get("txid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return super.responseFail(String.format("请求失败:%s", e.getMessage()));
        }
    }

    @Override
    public String chainCodeUpgrade(String[] args) {
        SdkManager manager = SdkManager.getInstance(fabricConfigMapper);
        Map<String, String> resultMap;
        try {
            resultMap = manager.chainCodeUpgrade(args);
            if (resultMap.get("code").equals("error")) {
                return super.responseFail(resultMap.get("data"));
            } else {
                return super.responseSuccess(resultMap.get("data"), resultMap.get("txid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return super.responseFail(String.format("请求失败:%s", e.getMessage()));
        }
    }
    @Override
    public String chainCodeInvoke(String fcn, String[] args) {
        SdkManager manager = SdkManager.getInstance(fabricConfigMapper);
        Map<String, String> resultMap;
        try {
            resultMap = manager.chainCodeInvoke(fcn, args);
            if (resultMap.get("code").equals("error")) {
                return super.responseFail(resultMap.get("data"));
            } else {
                return super.responseSuccess(resultMap.get("data"), resultMap.get("txid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return super.responseFail(String.format("请求失败:%s", e.getMessage()));
        }
    }

    @Override
    public String chainCodeQuery(String fcn, String[] args) {
        SdkManager manager = SdkManager.getInstance(fabricConfigMapper);
        Map<String, String> resultMap;
        try {
            resultMap = manager.chainCodeQuery(fcn, args);
            if (resultMap.get("code").equals("error")) {
                return super.responseFail(resultMap.get("data"));
            } else {
                return super.responseSuccess(resultMap.get("data"), resultMap.get("txid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return super.responseFail(String.format("请求失败:%s", e.getMessage()));
        }
    }
}
