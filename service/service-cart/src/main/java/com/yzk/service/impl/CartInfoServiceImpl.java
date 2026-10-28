package com.yzk.service.impl;


import com.yzk.client.ProductFeignClient;
import com.yzk.common.constant.RedisConst;
import com.yzk.common.exception.SsyxException;
import com.yzk.common.result.ResultCodeEnum;
import com.yzk.enums.SkuType;
import com.yzk.model.order.CartInfo;
import com.yzk.model.product.SkuInfo;
import com.yzk.service.CartInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CartInfoServiceImpl implements CartInfoService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;
    @Override
    public void addToCart(Long userId, Long skuId, Integer skuNum) {
        //1.设置redis key
        String cartKey = this.getCartKey(userId);
        //2.判断是否第一次添加购物车
        BoundHashOperations<String, String, CartInfo> hashOperations = redisTemplate.boundHashOps(cartKey);
        CartInfo cartInfo;
        if (hashOperations.hasKey(skuId.toString())) {
            //3.不是第一次添加只需记入商品数量和当前值
            //3.1取出购物车信息
            cartInfo = hashOperations.get(skuId.toString());

            Integer currentNum = cartInfo.getSkuNum() + skuNum;
            if (currentNum < 1) {
                return;
            }
            cartInfo.setSkuNum(currentNum);
            cartInfo.setCurrentBuyNum(currentNum);
            if (currentNum > cartInfo.getPerLimit()) {
                throw new SsyxException(ResultCodeEnum.SKU_LIMIT_ERROR);
            }
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        } else {
            //4.第一次添加加入商品的各种信息
            skuNum = 1;
            SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
            if (skuInfo == null) {
                throw new SsyxException(ResultCodeEnum.DATA_ERROR);
            }
            //封装cartInfo对象
            cartInfo = new CartInfo();
            cartInfo.setSkuId(skuId);
            cartInfo.setCategoryId(skuInfo.getCategoryId());
            cartInfo.setSkuType(skuInfo.getSkuType());
            cartInfo.setIsNewPerson(skuInfo.getIsNewPerson());
            cartInfo.setUserId(userId);
            cartInfo.setCartPrice(skuInfo.getPrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setCurrentBuyNum(skuNum);
            cartInfo.setSkuType(SkuType.COMMON.getCode());
            cartInfo.setPerLimit(skuInfo.getPerLimit());
            cartInfo.setImgUrl(skuInfo.getImgUrl());
            cartInfo.setSkuName(skuInfo.getSkuName());
            cartInfo.setWareId(skuInfo.getWareId());
            cartInfo.setIsChecked(1);
            cartInfo.setStatus(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
        //5.更新redis
        hashOperations.put(skuId.toString(), cartInfo);
        //6.设置过期时间
        this.setCartKeyExpire(cartKey);

    }

    /**
     * 设置过期时间
     *
     * @param cartKey
     * @return
     */
    private Boolean setCartKeyExpire(String cartKey) {
        return redisTemplate.expire(cartKey, RedisConst.USER_CART_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 获取购物车可以
     * @param userId 用户ID
     * @return
     */
    private String getCartKey(Long userId) {
        return RedisConst.USER_KEY_PREFIX+userId+RedisConst.USER_CART_KEY_SUFFIX;
    }

    @Override
    public void deleteCart(Long skuId, Long userId) {
        BoundHashOperations<String,String,CartInfo> hashOperations = redisTemplate.boundHashOps(this.getCartKey(userId));
        if(hashOperations.hasKey(skuId.toString())){
            hashOperations.delete(skuId.toString());
        }
    }

    @Override
    public void deleteAllCart(Long userId) {
        BoundHashOperations<String,String,CartInfo> hashOperations = redisTemplate.boundHashOps(this.getCartKey(userId));
        List<CartInfo> cartInfoList = hashOperations.values();
        for(CartInfo cartInfo:cartInfoList){
            hashOperations.delete(cartInfo.getId().toString());
        }

    }

    @Override
    public void batchDeleteCart(List<Long> skuIdList, Long userId) {
        BoundHashOperations<String,String,CartInfo> hashOperations = redisTemplate.boundHashOps(this.getCartKey(userId));
       skuIdList.forEach(skuId->{
           hashOperations.delete(skuId.toString());
       });
    }

    @Override
    public List<CartInfo> getCartList(Long userId) {
        //判断用户是否存在
        if(userId==null){
            return  Collections.emptyList();
        }
        //从redis获取购物车数据
        BoundHashOperations<String,String,CartInfo> hashOperations = redisTemplate.boundHashOps(this.getCartKey(userId));
       List<CartInfo> cartInfoList = hashOperations.values();
        if(!CollectionUtils.isEmpty(cartInfoList)){
            cartInfoList.sort(new Comparator<CartInfo>() {
                @Override
                public int compare(CartInfo o1, CartInfo o2) {
                    return o1.getCreateTime().compareTo(o2.getCreateTime());
                }
            });
        }
        return cartInfoList;

    }

    @Override
    public void checkCart(Long userId, Long skuId, Integer isChecked) {
        String cartKey = this.getCartKey(userId);
        BoundHashOperations<String,String,CartInfo> hashOperations = redisTemplate.boundHashOps(cartKey);

        CartInfo cartInfo = hashOperations.get(skuId.toString());

        cartInfo.setIsChecked(isChecked);
        hashOperations.put(cartInfo.getSkuId().toString(), cartInfo);
        this.setCartKeyExpire(cartKey);
    }

    @Override
    public void checkAllCart(Long userId, Integer isChecked) {
        String cartKey = this.getCartKey(userId);
        BoundHashOperations<String,String,CartInfo> hashOperations = redisTemplate.boundHashOps(cartKey);
        List<CartInfo> cartInfoList = hashOperations.values();

        for(CartInfo cartInfo:cartInfoList){
            cartInfo.setIsChecked(isChecked);
            hashOperations.put(cartInfo.getSkuId().toString(), cartInfo);
        }
        this.setCartKeyExpire(cartKey);
    }

    @Override
    public void batchCheckCart(List<Long> skuIdList, Long userId, Integer isChecked) {
        String cartKey = this.getCartKey(userId);
        BoundHashOperations<String,String,CartInfo> hashOperations = redisTemplate.boundHashOps(cartKey);

        skuIdList.forEach(skuId->{
            CartInfo cartInfo = hashOperations.get(skuId);
            cartInfo.setIsChecked(isChecked);
            hashOperations.put(cartInfo.getSkuId().toString(), cartInfo);
        });

        this.setCartKeyExpire(cartKey);
    }

    @Override
    public List<CartInfo> getCartCheckedList(Long userId) {
        String cartKey = this.getCartKey(userId);
        BoundHashOperations<String,String,CartInfo> hashOperations = redisTemplate.boundHashOps(cartKey);
        List<CartInfo> cartInfoList = hashOperations.values();
        return cartInfoList;
    }
}
