package com.hhdl.evtp.controller.rest;

import com.hhdl.common.model.CommonResult;
import com.hhdl.evtp.dao.FabricConfigMapper;
import com.hhdl.evtp.model.FabricConfigModel;
import com.hhdl.evtp.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/smartContracts")
public class FileUploadController {
    @Autowired
    private FabricConfigMapper fabricConfigMapper;

    /**
     * 实现文件上传
     */
    @RequestMapping("/fileUpload")
    public CommonResult fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return CommonResult.failed("请选择要上传的文件");
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        String path = "F:/test";
        File dest = new File(path + "/" + fileName);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return CommonResult.success("");
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    /**
     * 实现多文件上传
     */
    @RequestMapping(value = "/multifileUpload", method = RequestMethod.POST)
    public CommonResult multifileUpload(@RequestParam("file") MultipartFile[] fileMs, HttpServletRequest request) {

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");

        if (files.isEmpty()) {
            return CommonResult.failed("请选择要上传的文件");
        }
        HttpSession session = request.getSession();
        String id = session.getId();
        UserModel userModel = (UserModel) session.getAttribute(id);
        List<FabricConfigModel> configList = this.fabricConfigMapper.queryFabricConfig(String.valueOf(userModel.getLeague_id()));
        String path = "";
        if (configList.size() > 0) {
            FabricConfigModel fabricConfigModel = configList.get(0);
            path = fabricConfigModel.getChaincode_source() + "/src/" + fabricConfigModel.getChaincode_path();
        } else {
            return CommonResult.failed("请配置智能合约路径");
        }


        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);

            if (file.isEmpty()) {
                return CommonResult.failed("请选择要上传的文件");
            } else {
                File dest = new File(path + "/" + fileName);
                if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return CommonResult.failed(e.getMessage());
                }
            }
        }
        return CommonResult.success("");
    }
}
