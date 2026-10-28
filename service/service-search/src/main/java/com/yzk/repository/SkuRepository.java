package com.yzk.repository;

import com.yzk.model.search.SkuEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @description: sku es数据访问类
 * @author: yzk
 * @date: 2026-10-08 00:26
 * @version: 1.0
 * @email: yzk@qq.com
 */
public interface SkuRepository extends ElasticsearchRepository<SkuEs,Long> {

}