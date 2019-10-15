package com.hhdl.system.controller;

import com.hhdl.common.controller.BaseController;
import com.hhdl.common.model.CodeEnum;
import com.hhdl.common.model.Result;
import com.hhdl.system.model.User;
import com.hhdl.system.model.UserAddModel;
import com.hhdl.system.model.UserPage;
import com.hhdl.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "用户管理")
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "列表", httpMethod = "POST")
    @RequestMapping(value = "/list")
    @ResponseBody
    public Result list(@ModelAttribute UserPage user) {
        return  new Result(CodeEnum.SUCCESS.getCode(),userService.findTByPage(user));
    }

    @ApiOperation(value = "增加", httpMethod = "POST")
    @RequestMapping(value = "/add")
    @ResponseBody
    public Result add(@ModelAttribute UserAddModel user) {
        userService.insertUser(user);
        return new Result(CodeEnum.SUCCESS.getCode(), null);

    }

    @ApiOperation(value = "单条", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "form"),
    })
    @RequestMapping(value = "/selectByPrimaryKey")
    @ResponseBody
    public Result selectByPrimaryKey(String id) {
        return new Result(CodeEnum.SUCCESS.getCode(),userService.selectVOByPrimaryKey(id));
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @RequestMapping(value = "/update")
    @ResponseBody
    public Result update(@ModelAttribute UserAddModel muser) {
        Assert.notNull(muser.getId(),CodeEnum.NO_NULL.getCode());
        userService.updateUser(muser);
        return new Result(CodeEnum.SUCCESS.getCode(), null);

    }

    @ApiOperation(value = "删除", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "form"),
    })
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result delete(String[] id) {
        userService.deleteUser(id);
        return new Result(CodeEnum.SUCCESS.getCode(), null);

    }

    @ApiOperation(value = "是否重复", httpMethod = "POST")
    @RequestMapping(value = "/findUserByUser")
    @ResponseBody
    public Result findUserByUser(@ModelAttribute User user) {
        User tByT = userService.findTByT(user);
        String r = tByT == null ? CodeEnum.SUCCESS.getCode() : CodeEnum.NO_NULL.getCode();
        return new Result(r, null);

    }

    @ApiOperation(value = "密码查询", httpMethod = "POST")
    @RequestMapping(value = "/toUpdatePassword")
    @ResponseBody
    public Result toUpdatePassword() {
        return new Result(CodeEnum.SUCCESS.getCode(),"");
    }

    @ApiOperation(value = "更改密码", httpMethod = "POST")
    @RequestMapping(value = "/updatePassword")
    @ResponseBody
    public Result updatePassword(@ModelAttribute UserAddModel muser) {
        userService.updatePassword(muser);
        return new Result(CodeEnum.SUCCESS.getCode(), null);

    }

}
