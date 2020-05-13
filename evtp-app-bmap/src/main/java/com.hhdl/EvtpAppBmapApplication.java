package com.hhdl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@EnableCircuitBreaker
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class EvtpAppBmapApplication {
   /* @Value( "${spring.application.name}" )
    String appName;

    private static Logger logger = LoggerFactory.getLogger ( EvtpAppBmapApplication.class );

    private static String[] args;
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        EvtpAppBmapApplication.args = args;
        EvtpAppBmapApplication.context = SpringApplication.run(EvtpAppBmapApplication.class, args);
    }
    @GetMapping("/refresh")
    public String restart(){
        logger.info ( "spring.application.name:"+appName);
        ExecutorService threadPool = new ThreadPoolExecutor(1,1,0, TimeUnit.SECONDS,new ArrayBlockingQueue<>( 1 ),new ThreadPoolExecutor.DiscardOldestPolicy ());
        threadPool.execute (()->{
            context.close ();
            context = SpringApplication.run ( EvtpAppBmapApplication.class,args );
        } );
        threadPool.shutdown ();
        return "spring.application.name:"+appName;
    }

    @GetMapping("/info")
    public String info(){
        logger.info ( "spring.application.name:"+appName);
        return appName;
    }*/


    public static void main(String[] args) {
        SpringApplication.run(EvtpAppBmapApplication.class, args);
    }
}
