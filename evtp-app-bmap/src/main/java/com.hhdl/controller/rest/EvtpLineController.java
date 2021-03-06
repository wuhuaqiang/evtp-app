package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hhdl.common.model.CommonResult;
import com.hhdl.model.EvtpLine;
import com.hhdl.model.EvtpUser;
import com.hhdl.service.EvtpLineService;
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
 * @since 2019-08-06
 */
@RestController
@RequestMapping("/evtpLine")
public class EvtpLineController {
    @Autowired
    private EvtpLineService evtpLineService;
    @RequestMapping("/page")
    public CommonResult page(@RequestParam int page, int limit, String name) {
        Page<EvtpLine> evtpLinePage = new Page<>(page, limit);
        Wrapper<EvtpLine> evtpLineWrapper = new EntityWrapper<EvtpLine>();
        evtpLineWrapper.like("name",name);
        return CommonResult.success(evtpLineService.selectPage(evtpLinePage,evtpLineWrapper));
    }
    @RequestMapping("/list")
    public CommonResult getPage() {
        Wrapper<EvtpLine> evtpLineWrapper = new EntityWrapper<EvtpLine>();
        return CommonResult.success(evtpLineService.selectList(evtpLineWrapper));
    }

    @RequestMapping("/getOneById")
    public CommonResult getOneById(@RequestParam String id) {
        EvtpLine evtpLine = evtpLineService.selectById(id);
        return CommonResult.success(evtpLine);
    }

    @RequestMapping("/save")
    public CommonResult save(@RequestBody EvtpLine evtpLine) {
        try {
            evtpLineService.insert(evtpLine);
        } catch (Exception e) {
            System.out.println(e);
        }

        return CommonResult.success("");
    }
}

