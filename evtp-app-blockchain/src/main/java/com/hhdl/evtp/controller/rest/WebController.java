package com.hhdl.evtp.controller.rest;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.evtp.config.WebSecurityConfig;
import com.hhdl.evtp.dao.FabricConfigMapper;
import com.hhdl.evtp.model.FabricConfigModel;
import com.hhdl.evtp.model.FabricConfigOrdererModel;
import com.hhdl.evtp.model.FabricConfigPeerModel;
import com.hhdl.evtp.model.UserModel;
import com.hhdl.evtp.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by linwf on 2018/11/18.
 */
@Controller
@RequestMapping("/")
public class WebController {
    @Resource
    private WebService webService;
    @Autowired
    private FabricConfigMapper fabricConfigMapper;

    @RequestMapping(value = "/index")
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = session.getId();
        UserModel userModel = (UserModel) session.getAttribute(id);
        List<FabricConfigModel> configList = this.fabricConfigMapper.queryFabricConfig(String.valueOf(userModel.getLeague_id()));
//        Wrapper<FabricConfigModel> fabricConfigModelEntityWrapper = new EntityWrapper<FabricConfigModel>();
//        List<FabricConfigModel> configList = this.fabricConfigMapper.selectList(fabricConfigModelEntityWrapper);
        FabricConfigModel configData = null;
        List<FabricConfigOrdererModel> configOrdererList = null;
        List<FabricConfigPeerModel> configPeerList = null;
        if (configList != null && configList.size() > 0) {
            configData = configList.get(0);
            configOrdererList = fabricConfigMapper.queryFabricOrderer(configData.getLeague_id());
            configPeerList = fabricConfigMapper.queryFabricPeer(configData.getLeague_id());
        }
        model.addAttribute("config", configData);
        model.addAttribute("configOrdererList", configOrdererList);
        model.addAttribute("configPeerList", configPeerList);
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logon")
    public String logon(@RequestParam String account, @RequestParam String password, HttpSession session, RedirectAttributes attributes, Model model) {
        //判断用户名是否为空
        boolean checkAccount = (null == account || "".equals(account));
        //判断密码是否为空
        boolean checkPassword = (null == password || "".equals(password));
        if (checkAccount || checkPassword) {
            //attributes.addFlashAttribute("nullNameAndPassword", "用户名或密码不能为空");
            return "redirect:/login?error=true";
        }
        UserModel user = webService.getUser(account, password);
        if (null == user) {
            // attributes.addFlashAttribute("nullNameAndPassword", "用户名或密码错误");
            return "redirect:/login?error=true";
        }
        session.setAttribute(session.getId(), user);//WebSecurityConfig.SESSION_KEY
        model.addAttribute("user", user);
        return "redirect:/index";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, RedirectAttributes attributes) {
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/login";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String save(@RequestParam Map<String, Object> map) {
        JSONObject jsonObjectResult = new JSONObject();
        JSONObject jsonMap = new JSONObject(map);
        JSONObject json = jsonMap.getJSONObject("jsonParam");
        int result = webService.save(json);
        jsonObjectResult.put("result", result);
        return jsonObjectResult.toString();

        /*
        JSONObject jsonObjectResult = new JSONObject();
        int result = -1;
        int configId = 0;
        JSONObject jsonMap = new JSONObject(map);
        JSONObject json =  jsonMap.getJSONObject("jsonParam");
        FabricConfigModel fabricConfig = new FabricConfigModel();
        fabricConfig.setRow_id(json.getInteger("rowId"));
        fabricConfig.setLeague_id(1);
        fabricConfig.setOrg_name(json.getString("orgName"));
        fabricConfig.setUser_name(json.getString("userName"));
        fabricConfig.setCryptoconfig_path(json.getString("cryptoconfigPath"));
        fabricConfig.setChannel_name(json.getString("channelName"));
        fabricConfig.setOrg_mspid(json.getString("orgMspid"));
        fabricConfig.setOrg_domain(json.getString("orgDomain"));
        fabricConfig.setOrderer_domain(json.getString("ordererDomain"));
        fabricConfig.setChannel_name(json.getString("channelName"));
        fabricConfig.setChaincode_name(json.getString("chaincodeName"));
        fabricConfig.setChaincode_source(json.getString("chaincodeSource"));
        fabricConfig.setChaincode_path(json.getString("chaincodePath"));
        fabricConfig.setChaincode_policy(json.getString("chaincodePolicy"));
        fabricConfig.setChaincode_version(json.getString("chaincodeVersion"));
        fabricConfig.setProposal_waittime(json.getInteger("proposalWaittime"));
        fabricConfig.setInvoke_waittime(json.getInteger("invokeWaittime"));
        fabricConfig.setIs_tls(json.getBoolean("isTls"));
        fabricConfig.setIs_catls(json.getBoolean("isCatls"));
        //保存配置
        if (fabricConfig.getRow_id() > 0){
            result = this.fabricConfigMapper.modifyFabricConfig(fabricConfig);
            configId = result;
        }else{
            result = this.fabricConfigMapper.addFabricConfig(fabricConfig);
            configId = fabricConfig.getRow_id();
        }
        //orderer操作
        //删除全部orderer
        this.fabricConfigMapper.deleteFabricOrderer(-1);
        JSONArray arrayJson = json.getJSONArray("ordererList");
        for(int i = 0; i < arrayJson.size(); i++){
            JSONObject ordererItem = arrayJson.getJSONObject(i);
            FabricConfigOrdererModel fabricConfigOrderer = new FabricConfigOrdererModel();
            fabricConfigOrderer.setOrderer_name(ordererItem.getString("ordererName"));
            fabricConfigOrderer.setOrderer_location(ordererItem.getString("ordererLocation"));
            fabricConfigOrderer.setConfig_id(configId);
            //保存orderer
            this.fabricConfigMapper.addFabricOrderer(fabricConfigOrderer);
        }
        //peer操作
        //删除全部peer
        this.fabricConfigMapper.deleteFabricPeer(-1);
        arrayJson = json.getJSONArray("peerList");
        for(int i = 0; i < arrayJson.size(); i++){
            JSONObject peerItem = arrayJson.getJSONObject(i);
            FabricConfigPeerModel fabricConfigPeer = new FabricConfigPeerModel();
            fabricConfigPeer.setPeer_name(peerItem.getString("peerName"));
            fabricConfigPeer.setPeer_eventhubname(peerItem.getString("peerEventhubname"));
            fabricConfigPeer.setPeer_location(peerItem.getString("peerLocation"));
            fabricConfigPeer.setPeer_eventhublocation(peerItem.getString("peerEventhublocation"));
            fabricConfigPeer.setIs_eventlistener(peerItem.getBoolean("isEventListener"));
            fabricConfigPeer.setConfig_id(configId);
            //保存peer
            this.fabricConfigMapper.addFabricPeer(fabricConfigPeer);
        }
        if (result > 0){

        }
        jsonObjectResult.put("result", result);
        return jsonObjectResult.toString();
        */
    }

    @RequestMapping(value = "/modifypassword")
    @ResponseBody
    public String modifyPassword(@RequestParam Map<String, Object> map) {
        JSONObject jsonObjectResult = new JSONObject();
        JSONObject jsonMap = new JSONObject(map);
        String account = jsonMap.getString("account");
        String password = jsonMap.getString("password");
        int result = webService.modifyPassword(account, password);
        jsonObjectResult.put("result", result);
        return jsonObjectResult.toString();
    }
}
