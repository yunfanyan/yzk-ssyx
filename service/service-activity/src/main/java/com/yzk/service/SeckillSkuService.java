package com.yzk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.activity.Seckill;
import com.yzk.model.activity.SeckillSku;
import com.yzk.vo.activity.SeckillSkuQueryVo;
import com.yzk.vo.activity.SeckillSkuVo;
import com.yzk.vo.product.SkuStockLockVo;

import java.util.List;

/**
 * @description: 接口功能描述
 * @author: yzk
 * @date: 2026-10-09 02:03
 * @version: 1.0
 * @email: yzk@qq.com
 */
public interface SeckillSkuService extends IService<SeckillSku> {
    /**
     * 条件分页查询
     * @param pageParam
     * @param seckillSkuQueryVo
     * @return
     */
    IPage<SeckillSku> selectPage(Page<SeckillSku> pageParam, SeckillSkuQueryVo seckillSkuQueryVo);

    /**
     *
     * @param seckillSkuList
     */
    void save(List<SeckillSku> seckillSkuList);

    /**
     * 将某日的秒杀列表加载到缓存
     * @param date
     */
    void saveSeckillSkuListToCache(String date);

    /**
     * 从缓存中读取秒杀sku信息
     * @param timeName 场次名称
     */
    List<SeckillSkuVo> findSeckillSkuListFromCache(String timeName);

    /**
     * 根据skuId获取秒杀sku信息
     * @param skuId
     * @return
     */
    SeckillSkuVo getSeckillSkuVo(Long skuId);

    Boolean checkAndMinusStock(List<SkuStockLockVo> skuStockLockVoList, String orderToken);

    void rollBackStock(String orderNo);

}