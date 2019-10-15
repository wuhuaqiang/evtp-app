package com.hhdl.service;

import java.util.Map;

public interface EvtpTransactionService {
    Map<String, Object> transferAccounts(String userId, String csId, String money);
}
