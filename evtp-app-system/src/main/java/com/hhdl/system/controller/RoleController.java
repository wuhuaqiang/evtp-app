package com.hhdl.system.controller;


import com.hhdl.common.controller.BaseController;
import com.hhdl.common.model.CodeEnum;
import com.hhdl.common.model.Result;
import com.hhdl.system.model.RoleAddModel;
import com.hhdl.system.model.RolePage;
import com.hhdl.system.service.RoleService;
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

@Api(value = "角色管理")
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "列表", httpMethod = "POST")
    @RequestMapping(value = "/list")
    @ResponseBody
    public Result list(@ModelAttribute RolePage role) {
        return new Result(CodeEnum.SUCCESS.getCode(), roleService.findTByPage(role));
    }

    @ApiOperation(value = "增加", httpMethod = "POST")
    @RequestMapping(value = "/add")
    @ResponseBody
    public Result add(@ModelAttribute RoleAddModel role) {
        roleService.insertRole(role);
        return new Result(CodeEnum.SUCCESS.getCode(), null);

    }

    @ApiOperation(value = "删除", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "form"),
    })
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result delete(String[] id) {
        roleService.deleteRole(id);
        return new Result(CodeEnum.SUCCESS.getCode(), null);
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @RequestMapping(value = "/update")
    @ResponseBody
    public Result update(@ModelAttribute RoleAddModel mrole) {
        Assert.notNull(mrole.getId(), CodeEnum.NO_NULL.getCode());
        roleService.updateRole(mrole);
        return new Result(CodeEnum.SUCCESS.getCode(), null);
    }

    @ApiOperation(value = "复选框", httpMethod = "POST")
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public Result listAll() {
        return new Result(CodeEnum.SUCCESS.getCode(), roleService.selectAll());
    }

    @ApiOperation(value = "单条", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "form"),
    })
    @RequestMapping(value = "/selectByPrimaryKey")
    @ResponseBody
    public Result selectByPrimaryKey(String id) {
        return new Result(CodeEnum.SUCCESS.getCode(), roleService.selectByPrimaryKey(id));
    }

}
