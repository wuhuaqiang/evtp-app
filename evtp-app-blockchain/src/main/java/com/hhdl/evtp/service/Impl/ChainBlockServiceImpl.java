package com.hhdl.evtp.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.hhdl.evtp.dao.FabricConfigMapper;
import com.hhdl.evtp.sdk.SdkManager;
import com.hhdl.evtp.service.ChainBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by linwf on 2018/11/4.
 */
@Service("chainBlockService")
public class ChainBlockServiceImpl extends BaseServiceImpl implements ChainBlockService {
    @Autowired
    private FabricConfigMapper fabricConfigMapper;

    @Override
    public String queryBlockByTransactionID(String txID) {
        SdkManager manager = SdkManager.getInstance(fabricConfigMapper);
        Map<String, String> resultMap;
        try {
            resultMap = manager.queryBlockByTransactionID(txID);
            return super.responseSuccess(JSONObject.parseObject(resultMap.get("data")));
        } catch (Exception e) {
            e.printStackTrace();
            return super.responseFail(String.format("请求失败:%s", e.getMessage()));
        }
    }

    @Override
    public String queryBlockByHash(byte[] blockHash) {
        SdkManager manager = SdkManager.getInstance(fabricConfigMapper);
        Map<String, String> resultMap;
        try {
            resultMap = manager.queryBlockByHash(blockHash);
            return super.responseSuccess(JSONObject.parseObject(resultMap.get("data")));
        } catch (Exception e) {
            e.printStackTrace();
            return super.responseFail(String.format("请求失败:%s", e.getMessage()));
        }
    }

    @Override
    public String queryBlockByNumber(long blockNumber) {
        SdkManager manager = SdkManager.getInstance(fabricConfigMapper);
        Map<String, String> resultMap;
        try {
            resultMap = manager.queryBlockByNumber(blockNumber);
            return super.responseSuccess(JSONObject.parseObject(resultMap.get("data")));
        } catch (Exception e) {
            e.printStackTrace();
            return super.responseFail(String.format("请求失败:%s", e.getMessage()));
        }
    }

    @Override
    public String queryCurrentBlockInfo() {
        SdkManager manager = SdkManager.getInstance(fabricConfigMapper);
        Map<String, String> resultMap;
        try {
            resultMap = manager.queryCurrentBlockInfo();
            return super.responseSuccess(JSONObject.parseObject(resultMap.get("data")));
        } catch (Exception e) {
            e.printStackTrace();
            return super.responseFail(String.format("请求失败:%s", e.getMessage()));
        }
    }
}
