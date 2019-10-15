package com.hhdl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EvtpAppReptileApplication {

    public static void main(String[] args) {
//		new SpringApplicationBuilder(EvtpAppEurekaApplication.class).web(true).run(args);
        SpringApplication.run(EvtpAppReptileApplication.class, args);
    }

}