package com.hhdl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;


@EnableZuulProxy
@SpringCloudApplication
public class EvtpAppZuulApplication {
  
	public static void main(String[] args) {
//		new SpringApplicationBuilder(EvtpAppZuulApplication.class).web(true).run(args);
		SpringApplication.run(EvtpAppZuulApplication.class,args);
	}
	@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter();
	}
}