package com.yzk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.activity.Seckill;
import com.yzk.vo.activity.SeckillQueryVo;

/**
 * @description: 接口功能描述
 * @author: yzk
 * @date: 2026-10-09 02:03
 * @version: 1.0
 * @email: yzk@qq.com
 */
public interface SeckillService extends IService<Seckill> {
    /**
     * 条件分页查询
     * @param pageParam 分页条件
     * @param seckillQueryVo 查询条件
     * @return
     */
    IPage<Seckill> selectPage(Page<Seckill> pageParam, SeckillQueryVo seckillQueryVo);

    /**
     * 更新状态
     * @param id 秒杀ID
     * @param status 秒杀状状态
     */
    void updateStatus(Long id, Integer status);
}