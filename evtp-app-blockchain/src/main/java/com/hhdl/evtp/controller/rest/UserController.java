package com.hhdl.evtp.controller.rest;

import com.hhdl.common.model.CommonResult;
import com.hhdl.evtp.model.UserModel;
import com.hhdl.evtp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fabricUser")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list() {
        try {

            List<UserModel> list = userService.getAllUser();
            return CommonResult.success(list, "获取成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/saveFabricUser", method = RequestMethod.POST)
    public CommonResult saveFabricUser(@RequestBody UserModel userModel) {
        try {
            userService.addUser(userModel);
            return CommonResult.success("保存成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/updateFabricUser", method = RequestMethod.POST)
    public CommonResult updateFabricUser(@RequestBody UserModel userModel) {
        try {
            userService.updateUser(userModel);
            return CommonResult.success("更新成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    public CommonResult modifyPassword(@RequestBody String account, String password) {
        try {
            userService.modifyPassword(account, password);
            return CommonResult.success("更新成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/deleteFabricUser", method = RequestMethod.GET)
    public CommonResult deleteFabricUser(@RequestParam int row_id) {
        try {
            userService.deleteUserById(row_id);
            return CommonResult.success("删除成功!");
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }
}
