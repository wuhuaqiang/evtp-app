package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.common.model.CommonResult;
import com.hhdl.model.EvtpPointsType;
import com.hhdl.model.TUser;
import com.hhdl.service.EvtpPointsTypeService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/evtpPointsType")
public class EvtpPointsTypeController {
    @Autowired
    private EvtpPointsTypeService evtpPointsTypeService;

    @RequestMapping("/list")
    public CommonResult getPage() {
        Wrapper<EvtpPointsType> evtpPointsTypeWrapper = new EntityWrapper<EvtpPointsType>();
        return CommonResult.success(evtpPointsTypeService.selectList(evtpPointsTypeWrapper));
    }

    @RequestMapping("/save")
    public CommonResult save(@RequestBody EvtpPointsType evtpPointsType) {
        try {
            Integer maxKey = evtpPointsTypeService.getMaxKey();
            if (maxKey == null) {
                maxKey = 0;
            }
            if (evtpPointsType.getId() == null||"".equals(evtpPointsType.getId())) {
                evtpPointsType.setId(UUIDKey.getKey());
                evtpPointsType.setNum(maxKey+1);
            }
            evtpPointsTypeService.insertOrUpdate(evtpPointsType);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CommonResult.success("");
    }
    @RequestMapping("/update")
    public CommonResult update(@RequestBody EvtpPointsType evtpPointsType) {
        try {
            evtpPointsTypeService.insertOrUpdate(evtpPointsType);
        } catch (Exception e) {
            System.out.println(e);
        }

        return CommonResult.success("更新成功");
    }

    @RequestMapping("/del")
    public CommonResult del(@RequestParam String id) {
        try {
            evtpPointsTypeService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CommonResult.success("删除成功");
    }
}

