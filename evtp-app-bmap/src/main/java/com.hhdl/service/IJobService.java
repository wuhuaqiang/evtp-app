package com.hhdl.service;

import com.hhdl.entity.QuartzEntity;

import java.util.List;

public interface IJobService {
	
    List<QuartzEntity> listQuartzEntity(QuartzEntity quartz, Integer pageNo, Integer pageSize);
    
    Long listQuartzEntity(QuartzEntity quartz);	
}
