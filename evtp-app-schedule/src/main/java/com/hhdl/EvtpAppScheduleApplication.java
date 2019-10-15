package com.hhdl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.feign.EnableFeignClients;

//@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.hhdl.*.dao")
public class EvtpAppScheduleApplication {

	public static void main(String[] args) {

		SpringApplication.run(EvtpAppScheduleApplication.class, args);

	}


}