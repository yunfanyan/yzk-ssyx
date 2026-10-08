package com.yzk.service;

/**
 * @description: es-sku业务接口
 * @author: yzk
 * @date: 2026-10-08 00:30
 * @version: 1.0
 * @email: yzk@qq.com
 */
public interface SkuService {
    /**
     * 上架
     * @param skuId
     */
    void upperSku(Long skuId);

    /**
     * 下架
     * @param skuId
     */
    void lowerSku(Long skuId);
}