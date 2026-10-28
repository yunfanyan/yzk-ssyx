package com.yzk.service;


import com.yzk.model.order.CartInfo;

import java.util.List;

public interface CartInfoService {

    /**
     * 增加购物车
     * @param userId 用户ID
     * @param skuId 商品id
     * @param skuNum 商品数量
     */
    void addToCart(Long userId, Long skuId, Integer skuNum);

    /**
     * 根据skuId和userId删除购物车
     * @param skuId 商品ID
     * @param userId 用户ID
     */
    void deleteCart(Long skuId, Long userId);

    /**
     * 清空购物车
     * @param userId 用户ID
     */
    void deleteAllCart(Long userId);

    /**
     * 批量删除商品
     * @param skuIdList 商品id列表
     * @param userId 用户ID
     */
    void batchDeleteCart(List<Long> skuIdList, Long userId);

    /**
     * 获取购物车信息
     * @param userId 用户ID
     * @return
     */
    List<CartInfo> getCartList(Long userId);

    /**
     *
     * @param userId 用户ID
     * @param skuId 商品ID
     * @param isChecked 检查值
     */
    void checkCart(Long userId, Long skuId, Integer isChecked);

    /**
     *
     * @param userId 用户ID
     * @param isChecked 检查值
     */
    void checkAllCart(Long userId, Integer isChecked);

    /**
     *
     * @param skuIdList 商品Id集合
     * @param userId 用户ID
     * @param isChecked 检查值
     */
    void batchCheckCart(List<Long> skuIdList, Long userId, Integer isChecked);

    /**
     * 根据用户检查购物车
     * @param userId 用户ID
     * @return
     */
    List<CartInfo> getCartCheckedList(Long userId);
}
