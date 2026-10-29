package com.yzk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yzk.mapper.SeckillTimeMapper;

import com.yzk.model.activity.SeckillTime;

import com.yzk.service.SeckillTimeService;
import org.springframework.stereotype.Service;

/**
 * @description: 类功能描述
 * @date: 2026-10-09 02:05
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Service
public class SeckillTimeServiceImpl extends ServiceImpl<SeckillTimeMapper, SeckillTime> implements SeckillTimeService {

    @Override
    public void updateStatus(Long id, Integer status) {
        SeckillTime seckillTime = new SeckillTime();
        seckillTime.setId(id);
        seckillTime.setStatus(status);
        baseMapper.updateById(seckillTime);
    }
}