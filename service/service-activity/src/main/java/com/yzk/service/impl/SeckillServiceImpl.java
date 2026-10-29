package com.yzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.mapper.SeckillMapper;
import com.yzk.model.activity.Seckill;
import com.yzk.service.SeckillService;
import com.yzk.vo.activity.SeckillQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @description: 类功能描述
 * @date: 2026-10-09 02:05
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Service
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, Seckill> implements SeckillService {
    /**
     * 条件分页查询
     * @param pageParam 分页条件
     * @param seckillQueryVo 查询条件
     * @return
     */
    @Override
    public IPage<Seckill> selectPage(Page<Seckill> pageParam, SeckillQueryVo seckillQueryVo) {
        LambdaQueryWrapper<Seckill> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(seckillQueryVo.getTitle())){
            lambdaQueryWrapper.like(Seckill::getTitle, seckillQueryVo.getTitle());
        }
        if(!StringUtils.isEmpty(seckillQueryVo.getTitle())){
            lambdaQueryWrapper.eq(Seckill::getStatus,seckillQueryVo.getStatus());
        }
        return  baseMapper.selectPage(pageParam,lambdaQueryWrapper);

    }

    /**
     * 更新秒杀状态
     * @param id 秒杀ID
     * @param status 秒杀状态
     */
    @Override
    public void updateStatus(Long id, Integer status) {
        Seckill seckill = new Seckill();
        seckill.setStatus(status);
        seckill.setId(id);
        baseMapper.updateById(seckill);
    }
}