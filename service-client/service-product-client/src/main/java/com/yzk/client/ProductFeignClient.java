package com.yzk.client;

import com.yzk.enums.SkuType;
import com.yzk.model.product.Category;
import com.yzk.model.product.SkuInfo;
import com.yzk.vo.product.SkuInfoVo;
import com.yzk.vo.product.SkuStockLockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 商品feign客户端接口
 * @author: yzk
 * @date: 2026-10-07 22:17
 * @version: 1.0
 * @email: yzk@qq.com
 */
@FeignClient(value = "service-product")
public interface ProductFeignClient {
    //获取新人专享商品
    @GetMapping("/api/product/inner/findNewPersonSkuInfoList")
    public List<SkuInfo> findNewPersonSkuInfoList();

    //所有分类
    @GetMapping("/api/product/inner/findAllCategoryList")
    public List<Category> findAllCategoryList();

    @GetMapping("/api/product/inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") Long categoryId);
    //根据分类id获取分类列表
    @PostMapping("/api/product/inner/findCategoryList")
    public List<Category> findCategoryList(@RequestBody List<Long> categoryIdList);

    @GetMapping("/api/product/inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable Long skuId);

    @PostMapping("/api/product/inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList);

    //@ApiOperation(value = "根据关键字获取sku列表")
    @GetMapping("/api/product/inner/findSkuInfoByKeyword/{keyword}")
    public List<SkuInfo> findSkuInfoByKeyword(@PathVariable("keyword") String keyword);
    //修改sku状态
    @PutMapping("/api/product/inner/updateSkuType/{skuIdList}/{skuType}")
    public void updateSkuType(@PathVariable List<Long> skuIdList, @PathVariable SkuType skuType);
}
