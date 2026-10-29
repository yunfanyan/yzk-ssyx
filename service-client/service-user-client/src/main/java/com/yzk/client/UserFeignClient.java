package com.yzk.client;

import com.yzk.vo.user.LeaderAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description: User模块feign 消费者
 * @date: 2026-10-11 10:04
 * @version: 1.0
 * @email: yzk@qq.com
 */

@FeignClient("service-user")
public interface UserFeignClient {

    //根据userId查询提货点和团长信息
    @GetMapping("/api/user/leader/inner/getUserAddressByUserId/{userId}")
    public LeaderAddressVo getUserAddressByUserId(@PathVariable("userId") Long userId);
}

