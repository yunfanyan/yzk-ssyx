package com.yzk.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.product.SkuInfo;
import com.yzk.vo.product.SkuInfoQueryVo;
import com.yzk.vo.product.SkuInfoVo;

import java.util.List;


/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
public interface SkuInfoService extends IService<SkuInfo> {

    /**
     * 商品sku信息新增
     * @param skuInfoVo 信息条件
     */
    void saveSkuInfo(SkuInfoVo skuInfoVo);

    /**
     * 商品sku信息修改
     * @param skuInfoVo 修改条件
     */
    void updateSkuInfo(SkuInfoVo skuInfoVo);

    /**
     * 根据id获取sku信息
     * @param id
     * @return
     */
    SkuInfoVo getSkuInfo(Long id);

    /**
     * 分页条件查询
     * @param pageParam 分页条件
     * @param skuInfoQueryVo 查询条件
     * @return
     */
    IPage<SkuInfo> selectPageSkuInfo(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo);

    /**
     * 商品审核
     * @param skuId
     * @param status sku状态
     */
    void check(Long skuId, Integer status);

    /**
     * 商品上下架
     * @param skuId
     * @param status
     */
    void publish(Long skuId, Integer status);

    /**
     * 新人专享
     * @param skuId
     * @param status
     */
    void isNewPerson(Long skuId, Integer status);

    /**
     * 批量获取sku信息
     * @param skuIdList
     * @return
     */
    List<SkuInfo> findSkuInfoList(List<Long> skuIdList);

    /**
     * 根据关键字或者sku信息
     * @param keyword
     * @return
     */
    List<SkuInfo> findSkuInfoByKeyword(String keyword);
}
