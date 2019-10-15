package com.hhdl.service;

import com.baomidou.mybatisplus.service.IService;
import com.hhdl.model.TTask;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-28
 */
public interface TTaskService extends IService<TTask> {
    public TTask getOwerTask(String owerId);

    public TTask getOwerNewTask(String owerId);
}
