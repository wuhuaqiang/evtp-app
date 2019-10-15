package com.hhdl.system.api.impl;

import com.hhdl.common.controller.BaseController;
import com.hhdl.common.model.CodeEnum;
import com.hhdl.common.model.Result;
import com.hhdl.system.model.Resources;
import com.hhdl.system.model.User;
import com.hhdl.system.model.UserDO;
import com.hhdl.system.service.ResourcesService;
import com.hhdl.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/api")
@Api(value = "SystemAPI")
public class SystemAPIImpl extends BaseController {


    @Autowired
    private UserService userService;

    @Autowired
    private ResourcesService resourcesService;

    @ApiOperation(value = "获取用户", httpMethod = "POST")
    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ResponseBody
    public Result<UserDO> getUser(@RequestBody UserDO u) {
        return new Result(CodeEnum.SUCCESS.getCode(), u);
    }



}
