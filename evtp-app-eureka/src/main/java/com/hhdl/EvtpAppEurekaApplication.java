package com.hhdl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EvtpAppEurekaApplication {

	public static void main(String[] args) {
//		new SpringApplicationBuilder(EvtpAppEurekaApplication.class).web(true).run(args);
		SpringApplication.run(EvtpAppEurekaApplication.class,args);
	}

}