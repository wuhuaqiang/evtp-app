package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hhdl.model.TUser;
import com.hhdl.service.TUserService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2018-12-03
 */
@RestController
@RequestMapping("/tUser")
public class TUserController {
    @Autowired
    private TUserService tUserService;

    @RequestMapping("/list")
    public List<TUser> getList() {
        Wrapper<TUser> tUserWrapper = new EntityWrapper<TUser>();
        return tUserService.selectList(tUserWrapper);
    }

    @RequestMapping("/save")
    public String saveUser(@RequestBody TUser tUser) {
        try {
            if (tUser.getId() == null) {
                tUser.setId(UUIDKey.getKey());
            }
            tUserService.insertOrUpdate(tUser);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/update")
    public String updateUser(@RequestBody TUser tUser) {
        try {
            tUserService.insertOrUpdate(tUser);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/delbyId")
    public String delbyId(@RequestBody String id) {
        try {
            tUserService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "success";
    }

}

