package com.hhdl.evtp.controller.rest;

import com.hhdl.common.model.CommonResult;
import com.hhdl.evtp.model.FabricConfigPeerModel;
import com.hhdl.evtp.service.FabricOrdererService;
import com.hhdl.evtp.service.FabricPeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fabricPeer")
public class FabricPeerController {
    @Autowired
    private FabricPeerService fabricPeerService;

    @RequestMapping(value = "/saveFabricPeer", method = RequestMethod.POST)
    public CommonResult saveFabricOrderer(@RequestBody FabricConfigPeerModel fabricConfigPeerModel) {
        try {
            fabricPeerService.save(fabricConfigPeerModel);
            return CommonResult.success("", "保存成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/updateFabricPeer", method = RequestMethod.POST)
    public CommonResult updateFabricOrderer(@RequestBody FabricConfigPeerModel fabricConfigPeerModel) {
        try {
            fabricPeerService.update(fabricConfigPeerModel);
            return CommonResult.success("", "更新成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list() {
        try {
            FabricConfigPeerModel fabricConfigPeerModel = new FabricConfigPeerModel();
            List<FabricConfigPeerModel> list = fabricPeerService.list(fabricConfigPeerModel);
            return CommonResult.success(list, "保存成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/delById", method = RequestMethod.GET)
    public CommonResult delById(@RequestParam String id) {
        try {
            fabricPeerService.del(id);
            return CommonResult.success("", "删除成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }
}
