package com.hhdl.evtp.controller.rest;

import com.hhdl.common.model.CommonResult;
import com.hhdl.common.model.ResultCode;
import com.hhdl.evtp.model.FabricConfigModel;
import com.hhdl.evtp.service.FabricConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fabricConfig")
public class FabricConfigController {
    @Autowired
    private FabricConfigService fabricConfigService;

    @RequestMapping(value = "/saveFabricConfig", method = RequestMethod.POST)
    public CommonResult saveFabricConfig(@RequestBody FabricConfigModel fabricConfigModel) {
        try {
            fabricConfigService.save(fabricConfigModel);
            return CommonResult.success("", "保存成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/updateFabricConfig", method = RequestMethod.POST)
    public CommonResult updateFabricConfig(@RequestBody FabricConfigModel fabricConfigModel) {
        try {
            fabricConfigService.update(fabricConfigModel);
            return CommonResult.success("", "更新成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list() {
        try {
            FabricConfigModel fabricConfigModel = new FabricConfigModel();
            List<FabricConfigModel> list = fabricConfigService.list(fabricConfigModel);
            return CommonResult.success(list, "成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/delById", method = RequestMethod.GET)
    public CommonResult delById(@RequestParam String id) {
        try {
            fabricConfigService.del(id);
            return CommonResult.success("", "删除成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }
}
