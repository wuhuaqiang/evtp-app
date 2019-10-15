package com.hhdl.system.api;

import com.hhdl.common.model.Result;
import com.hhdl.system.model.UserDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("springcloud-app-system")
public interface SystemAPI {


    @RequestMapping(value = "/springcloud-app-system/api/getUser", method = RequestMethod.POST)
    Result<UserDO> getUser(@RequestBody UserDO u);


}