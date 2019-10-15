package com.hhdl.evtp.controller.rest;

import com.hhdl.common.model.CommonResult;
import com.hhdl.evtp.common.FabricCa1;
import com.hhdl.evtp.common.FabricCa2;
import com.hhdl.evtp.common.FabricCa3;
import com.hhdl.evtp.dao.FabricConfigMapper;
import com.hhdl.evtp.javaCAsdk.FabricCA;
import com.hhdl.evtp.model.FabricConfigModel;
import com.hhdl.evtp.model.TElectricVehicle;
import com.hhdl.evtp.model.TUser;
import com.hhdl.evtp.model.UserModel;
import com.hhdl.evtp.service.Impl.RedisCacheService;
import com.hhdl.evtp.service.TElectricVehicleService;
import com.hhdl.evtp.service.TUserService;
import com.hhdl.evtp.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Resource
    private WebService webService;
    @Autowired
    private FabricConfigMapper fabricConfigMapper;
    @Autowired
    private TUserService tUserService;
    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private TElectricVehicleService tElectricVehicleService;

    @RequestMapping(value = "/logon", method = RequestMethod.POST)
    public CommonResult logon(@RequestBody UserModel userModel, HttpSession session) {
        //判断用户名是否为空
        boolean checkAccount = (null == userModel.getAccount() || "".equals(userModel.getAccount()));
        //判断密码是否为空
        boolean checkPassword = (null == userModel.getPassword() || "".equals(userModel.getPassword()));
        if (checkAccount || checkPassword) {
            //attributes.addFlashAttribute("nullNameAndPassword", "用户名或密码不能为空");
            return CommonResult.failed("用户名或者密码不能为空!");
        }
        UserModel user = webService.getUser(userModel.getAccount(), userModel.getPassword());
        if (user == null) {
            return CommonResult.failed("用户名或者密码错误!");
        }
        TUser tUser = null;
        TElectricVehicle tElectricVehicle = null;
        if (user.getUser_info_id() != null) {
            tUser = tUserService.selectById(user.getUser_info_id());
            tElectricVehicle = tElectricVehicleService.selectById(tUser.getEvId());
        }
        Map result = new HashMap();
        result.put("user", user);
        result.put("userInfo", tUser);
        result.put("evInfo", tElectricVehicle);
        session.setAttribute(session.getId(), user);
        redisCacheService.set("sessionId",session.getId().toString(),24*60*60*1000);
        return CommonResult.success(result, "登录成功!");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult register(@RequestBody UserModel userModel, HttpSession session) {
        //判断用户名是否为空
        boolean checkAccount = (null == userModel.getAccount() || "".equals(userModel.getAccount()));
        //判断密码是否为空
        boolean checkPassword = (null == userModel.getPassword() || "".equals(userModel.getPassword()));
        if (checkAccount || checkPassword) {
            //attributes.addFlashAttribute("nullNameAndPassword", "用户名或密码不能为空");
            return CommonResult.failed("用户名或者密码不能为空!");
        }
        try {
            webService.addUser(userModel);
            FabricConfigModel fabricConfigModel = fabricConfigMapper.queryFabricConfig(String.valueOf(userModel.getLeague_id())).get(0);
            if ("Org1MSP".equals(fabricConfigModel.getOrg_mspid())) {
//                FabricCA.registerUser(FabricCa1.ORGNAME, FabricCa1.MSPID, FabricCa1.CALOCATION, FabricCa1.CANAME, FabricCa1.ADMINNAME, FabricCa1.ADMINPASSWD, userModel.getAccount(), userModel.getPassword(), FabricCa1.AFFILIATION1);
                FabricCA.registerUser(FabricCa1.ORGNAME, FabricCa1.MSPID, fabricConfigModel.getCa_location(), fabricConfigModel.getCa_name(), FabricCa1.ADMINNAME, FabricCa1.ADMINPASSWD, userModel.getAccount(), userModel.getPassword(), FabricCa1.AFFILIATION1);
            }
            if ("Org2MSP".equals(fabricConfigModel.getOrg_mspid())) {
//                FabricCA.registerUser(FabricCa2.ORGNAME, FabricCa2.MSPID, FabricCa2.CALOCATION, FabricCa2.CANAME, FabricCa2.ADMINNAME, FabricCa2.ADMINPASSWD, userModel.getAccount(), userModel.getPassword(), FabricCa2.AFFILIATION1);
                FabricCA.registerUser(FabricCa2.ORGNAME, FabricCa2.MSPID, fabricConfigModel.getCa_location(), fabricConfigModel.getCa_name(), FabricCa2.ADMINNAME, FabricCa2.ADMINPASSWD, userModel.getAccount(), userModel.getPassword(), FabricCa2.AFFILIATION1);
            }
            if ("Org3MSP".equals(fabricConfigModel.getOrg_mspid())) {
//                FabricCA.registerUser(FabricCa3.ORGNAME, FabricCa3.MSPID, FabricCa3.CALOCATION, FabricCa3.CANAME, FabricCa3.ADMINNAME, FabricCa3.ADMINPASSWD, userModel.getAccount(), userModel.getPassword(), FabricCa3.AFFILIATION1);
                FabricCA.registerUser(FabricCa3.ORGNAME, FabricCa3.MSPID, fabricConfigModel.getCa_location(), fabricConfigModel.getCa_name(), FabricCa3.ADMINNAME, FabricCa3.ADMINPASSWD, userModel.getAccount(), userModel.getPassword(), FabricCa3.AFFILIATION1);
            }

            return CommonResult.success("注册成功!");
        } catch (Exception e) {
            CommonResult.failed("注册失败!");
        }
        return CommonResult.success("注册成功!");
    }
}
