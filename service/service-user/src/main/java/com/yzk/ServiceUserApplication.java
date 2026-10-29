package com.yzk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户模块启动类
 *
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceUserApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ServiceUserApplication.class,args);
    }
}
