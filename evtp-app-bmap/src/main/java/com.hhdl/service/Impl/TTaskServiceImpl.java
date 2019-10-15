package com.hhdl.service.Impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hhdl.dao.TTaskDao;
import com.hhdl.model.TTask;
import com.hhdl.service.TTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-28
 */
@Service
public class TTaskServiceImpl extends ServiceImpl<TTaskDao, TTask> implements TTaskService {
    public TTask getOwerTask(String owerId) {
        Wrapper<TTask> tTaskWrapper = new EntityWrapper<TTask>();
        tTaskWrapper.where("ower_id={0}", owerId).and("state=0")
                .orderBy("sort", true);
        List<TTask> tTasks = this.selectList(tTaskWrapper);
        if (tTasks.size() > 0) {
            return tTasks.get(0);
        } else {
            return null;
        }

    }

    public TTask getOwerNewTask(String owerId) {
        Wrapper<TTask> tTaskWrapper = new EntityWrapper<TTask>();
        tTaskWrapper.where("ower_id={0}", owerId).and("state=0")
                .orderBy("sort", false);
        List<TTask> tTasks = this.selectList(tTaskWrapper);
        if (tTasks.size() > 0) {
            return tTasks.get(0);
        } else {
            return null;
        }

    }

}
