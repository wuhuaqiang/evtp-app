package com.hhdl.service.Impl;

import com.hhdl.model.EvtpChargingStation;
import com.hhdl.model.EvtpUser;
import com.hhdl.service.EvtpChargingStationService;
import com.hhdl.service.EvtpTransactionService;
import com.hhdl.service.EvtpUserService;
import com.hhdl.util.ArithUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EvtpTransactionServiceImpl implements EvtpTransactionService {
    @Autowired
    private EvtpUserService evtpUserService;
    @Autowired
    private EvtpChargingStationService evtpChargingStationService;

    @Override
    public Map<String, Object> transferAccounts(String userId, String csId, String money) {
        EvtpUser evtpUser = evtpUserService.selectById(userId);
        EvtpChargingStation evtpChargingStation = evtpChargingStationService.selectById(csId);
        Map<String, Object> result = new HashMap<>();
        String buyAccount = evtpUser.getAccount();
        String sellAccount = evtpChargingStation.getAccount();
        buyAccount = String.valueOf(ArithUtil.sub(Double.valueOf(buyAccount), Double.valueOf(money)));
        sellAccount = String.valueOf(ArithUtil.add(Double.valueOf(sellAccount), Double.valueOf(money)));
        evtpUser.setAccount(buyAccount);
        evtpChargingStation.setAccount(sellAccount);
        evtpUserService.updateById(evtpUser);
        evtpChargingStationService.updateById(evtpChargingStation);
        result.put("evtpUser", evtpUser);
        result.put("evtpChargingStation", evtpChargingStation);
        return result;
    }
}
