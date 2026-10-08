package com.yzk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 活动模块启动类
 *
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceActivityApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ServiceActivityApplication.class,args);
    }
}
