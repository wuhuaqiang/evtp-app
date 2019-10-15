package com.hhdl.controller.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hhdl.model.TTransaction;
import com.hhdl.service.TTransactionService;
import com.hhdl.util.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2018-12-17
 */
@RestController
@RequestMapping("/tTransaction")
public class TTransactionController {

    @Autowired
    private TTransactionService tTransactionService;

    @RequestMapping("/list")
    public List<TTransaction> getPage() {
        Wrapper<TTransaction> tTransactionWrapper = new EntityWrapper<TTransaction>();
        tTransactionWrapper.orderBy("block_number", true);
        return tTransactionService.selectList(tTransactionWrapper);
    }

    @RequestMapping("/save")
    public String saveLine(@RequestBody TTransaction tTransaction) {
        try {
            if (tTransaction.getTxId() == null) {
                tTransaction.setTxId(UUIDKey.getKey());
            }
            tTransactionService.insertOrUpdate(tTransaction);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }

    @RequestMapping("/delbyId")
    public String delbyId(@RequestBody String id) {
        try {
            tTransactionService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "success";
    }


    @RequestMapping("/page")
    public Page getPages(@RequestBody Map map) {
        int pages = (int) map.get("page");
        int size = (int) map.get("size");

        Page<TTransaction> page = new Page<TTransaction>(pages, size);
        Wrapper<TTransaction> tTransactionWrapper = new EntityWrapper<TTransaction>();
        tTransactionWrapper.orderBy("block_number", true);
        return tTransactionService.selectPage(page, tTransactionWrapper);
    }
}

