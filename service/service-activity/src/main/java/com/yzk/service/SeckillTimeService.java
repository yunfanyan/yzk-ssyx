package com.yzk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.activity.Seckill;
import com.yzk.model.activity.SeckillTime;

/**
 * @description: 接口功能描述
 * @author: yzk
 * @date: 2026-10-09 02:03
 * @version: 1.0
 * @email: yzk@qq.com
 */
public interface SeckillTimeService extends IService<SeckillTime> {
    /**
     * 更新秒杀时间状态
     * @param id ID
     * @param status
     */
    void updateStatus(Long id, Integer status);
}