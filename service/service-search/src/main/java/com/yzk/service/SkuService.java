package com.yzk.service;


import com.yzk.model.search.SkuEs;
import com.yzk.vo.search.SkuEsQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    /**
     * 获取热销商品列表
     * @return
     */
    List<SkuEs> findHotSkuList();

    /**
     * 条件分页查询商品列表
     * @param pageable 分页条件
     * @param searchParamVo 查询条件
     * @return
     */
    Page<SkuEs> search(Pageable pageable, SkuEsQueryVo searchParamVo);
}