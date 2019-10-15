package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.common.model.CommonResult;
import com.hhdl.model.EvtpSimulationParameters;
import com.hhdl.service.EvtpSimulationParametersService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-08-02
 */
@RestController
@RequestMapping("/evtpSimulationParameters")
public class EvtpSimulationParametersController {
    @Autowired
    private EvtpSimulationParametersService evtpSimulationParametersService;

    @RequestMapping("/list")
    public CommonResult list() {
        Wrapper<EvtpSimulationParameters> evtpSimulationParametersWrapper = new EntityWrapper<EvtpSimulationParameters>();
        return CommonResult.success(evtpSimulationParametersService.selectList(evtpSimulationParametersWrapper));
    }

    @RequestMapping("/save")
    public CommonResult save(@RequestBody EvtpSimulationParameters evtpSimulationParameters) {
        try {
            if (evtpSimulationParameters.getId() == null||"".equals(evtpSimulationParameters.getId())) {
                evtpSimulationParameters.setId(UUIDKey.getKey());
            }
            evtpSimulationParametersService.insertOrUpdate(evtpSimulationParameters);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CommonResult.success("");
    }
    @RequestMapping("/update")
    public CommonResult update(@RequestBody EvtpSimulationParameters evtpSimulationParameters) {
        try {
            evtpSimulationParametersService.insertOrUpdate(evtpSimulationParameters);
        } catch (Exception e) {
            System.out.println(e);
        }

        return CommonResult.success("更新成功");
    }

    @RequestMapping("/del")
    public CommonResult del(@RequestParam String id) {
        try {
            evtpSimulationParametersService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return CommonResult.success("删除成功");
    }
}

