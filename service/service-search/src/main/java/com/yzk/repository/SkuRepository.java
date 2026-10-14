package com.yzk.repository;

import com.yzk.model.search.SkuEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @description: sku es数据访问类
 * @author: yzk
 * @date: 2026-10-08 00:26
 * @version: 1.0
 * @email: yzk@qq.com
 */
public interface SkuRepository extends ElasticsearchRepository<SkuEs,Long> {
    /**
     * 获取热销商品列表
     * @param pageable
     * @return
     */
    Page<SkuEs> findByOrderByHotScoreDesc(Pageable pageable);

    /**
     * 根据分类id查询和仓库id查询商品列表
     * @param categoryId
     * @param wareId
     * @param pageable
     * @return
     */
    Page<SkuEs> findByCategoryIdAndWareId(Long categoryId, Long wareId, Pageable pageable);

    ///如果keyword不为空根据仓库id + keyword进行查询
    Page<SkuEs> findByKeywordAndWareId(String keyword, Long wareId, Pageable pageable);
}