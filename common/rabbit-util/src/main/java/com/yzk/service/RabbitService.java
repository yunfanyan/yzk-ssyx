package com.yzk.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 接口功能描述
 * @author: yzk
 * @date: 2026-10-08 02:01
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Service
public class RabbitService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param exchange 交换机
     * @param routingKey 路由
     * @param message 消息
     * @return
     */
    public boolean sendMegger(String exchange,String routingKey,Object message){
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        return true;
    }

}