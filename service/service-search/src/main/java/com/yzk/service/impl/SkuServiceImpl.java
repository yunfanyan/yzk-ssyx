package com.yzk.service.impl;


import com.yzk.client.ProductFeignClient;
import com.yzk.common.exception.SsyxException;
import com.yzk.enums.SkuType;
import com.yzk.model.product.Category;
import com.yzk.model.product.SkuInfo;
import com.yzk.model.search.SkuEs;
import com.yzk.repository.SkuRepository;
import com.yzk.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: es-sku业务实现类
 * @date: 2026-10-08 00:31
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private SkuRepository skuRepository;
    /**
     * 上架
     * @param skuId
     */
    @Override
    public void upperSku(Long skuId) {
        //1.查询sku信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if(skuInfo==null){
            throw new SsyxException("查询失败!", 201);
        }
        //2.查询sku分组信息
        Category category = productFeignClient.getCategory(skuInfo.getCategoryId());
        //2 获取数据封装SkuEs对象
        SkuEs skuEs = new SkuEs();
        //封装分类
        if(category != null) {
            skuEs.setCategoryId(category.getId());
            skuEs.setCategoryName(category.getName());
        }
        //封装sku信息部分
        skuEs.setId(skuInfo.getId());
        skuEs.setKeyword(skuInfo.getSkuName()+","+skuEs.getCategoryName());
        skuEs.setWareId(skuInfo.getWareId());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());
        if(skuInfo.getSkuType() == SkuType.COMMON.getCode()) {//普通商品
            skuEs.setSkuType(0);
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        }else {
            //TODO 待完善-秒杀商品

        }
        //es中新增
        skuRepository.save(skuEs);

    }

    /**
     * 下架
     * @param skuId
     */
    @Override
    public void lowerSku(Long skuId) {
        //es中删除
        skuRepository.deleteById(skuId);
    }
}