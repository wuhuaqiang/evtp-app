package com.hhdl.evtp.controller.rest;

import com.hhdl.common.model.CommonResult;
import com.hhdl.evtp.model.FabricConfigOrdererModel;
import com.hhdl.evtp.service.FabricOrdererService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fabricOrderer")
public class FabricOrdererController {
    @Autowired
    private FabricOrdererService fabricOrdererService;

    @RequestMapping(value = "/saveFabricOrderer", method = RequestMethod.POST)
    public CommonResult saveFabricOrderer(@RequestBody FabricConfigOrdererModel fabricConfigOrdererModel) {
        try {
            fabricOrdererService.save(fabricConfigOrdererModel);
            return CommonResult.success("", "保存成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/updateFabricOrderer", method = RequestMethod.POST)
    public CommonResult updateFabricOrderer(@RequestBody FabricConfigOrdererModel fabricConfigOrdererModel) {
        try {
            fabricOrdererService.update(fabricConfigOrdererModel);
            return CommonResult.success("", "更新成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list() {
        try {
            FabricConfigOrdererModel fabricConfigOrdererModel = new FabricConfigOrdererModel();
            List<FabricConfigOrdererModel> list = fabricOrdererService.list(fabricConfigOrdererModel);
            return CommonResult.success(list, "保存成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/delById", method = RequestMethod.GET)
    public CommonResult delById(@RequestParam String id) {
        try {
            fabricOrdererService.del(id);
            return CommonResult.success("", "删除成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }
}
