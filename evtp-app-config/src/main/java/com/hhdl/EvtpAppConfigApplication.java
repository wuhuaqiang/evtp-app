package com.hhdl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class EvtpAppConfigApplication {

	public static void main(String[] args) {
//		new SpringApplicationBuilder(EvtpAppConfigApplication.class).web(true).run(args);
        SpringApplication.run(EvtpAppConfigApplication.class,args);
	}

}