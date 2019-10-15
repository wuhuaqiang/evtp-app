package com.hhdl.system.controller;


import com.hhdl.common.controller.BaseController;
import com.hhdl.common.model.CodeEnum;
import com.hhdl.common.model.Result;
import com.hhdl.system.model.Resources;
import com.hhdl.system.service.ResourcesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "权限管理")
@Controller
@RequestMapping("/system/resources")
public class ResourcesController extends BaseController {
    @Autowired
    private ResourcesService resourcesService;

    @ApiOperation(value = "增加", httpMethod = "POST")
    @RequestMapping(value = "/add")
    @ResponseBody
    public Result add(@ModelAttribute Resources resources) {
        resourcesService.insertResources(resources);
        return new Result(CodeEnum.SUCCESS.getCode(), null);
    }

    @ApiOperation(value = "删除", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "form"),
    })

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result delete(String[] id) {
        resourcesService.deleteResources(id);
        return new Result(CodeEnum.SUCCESS.getCode(), null);
    }

    @ApiOperation(value = "更新", httpMethod = "POST")

    @RequestMapping(value = "/update")
    @ResponseBody
    public Result update(@ModelAttribute Resources resources) {
        resourcesService.updateResources(resources);
        return new Result(CodeEnum.SUCCESS.getCode(), null);

    }

    @ApiOperation(value = "列表", httpMethod = "POST")

    @RequestMapping(value = "/list")
    @ResponseBody
    public Result list() {
        return new Result(CodeEnum.SUCCESS.getCode(), resourcesService.findResources());
    }


    @ApiOperation(value = "权限码列表", httpMethod = "POST")
    @RequestMapping(value = "/findResourcesEMUByResources")
    @ResponseBody
    public Result findResourcesEMUByResources() {
        return new Result(CodeEnum.SUCCESS.getCode(), "");
    }

    @ApiOperation(value = "单条", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "form"),
    })
    @RequestMapping(value = "/selectByPrimaryKey")
    @ResponseBody
    public Result selectByPrimaryKey(String id) {
        return new Result(CodeEnum.SUCCESS.getCode(), resourcesService.selectByPrimaryKey(id));
    }

}
