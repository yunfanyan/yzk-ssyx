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
<<<<<<< HEAD

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

    /**
     * 更新商品热度
     * @param skuId
     * @return
     */
    Boolean incrHotScore(Long skuId);
=======
>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)
}