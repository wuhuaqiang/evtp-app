package com.hhdl.system.service.impl;


import com.hhdl.common.model.CodeEnum;
import com.hhdl.system.service.LoginService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class LoginServiceImpl implements LoginService {





    @Override
    public void logoutJSON() {

    }

    @Override
    public String getStatus(HttpServletRequest request) {
        return request.getAttribute("javax.servlet.error.status_code").toString();
    }

}


