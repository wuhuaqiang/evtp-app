package com.hhdl.config;

import com.hhdl.entity.EventQueue;
import com.hhdl.entity.PageInfoQueue;
import com.hhdl.entity.UrlQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zjb on 2019/3/21.
 */
@Configuration
public class QueueAndTaskConfig {

    @Bean("urlQueue")
    public EventQueue<String> urlQueue() {
        return new UrlQueue();
    }

    @Bean("pageInfoQueue")
    public EventQueue<String> pageInfoQueue() {
        return new PageInfoQueue();
    }

    @Bean("chm")
    public ConcurrentHashMap<String, Object> chm() {
        return new ConcurrentHashMap<>();
    }
}
