package com.yzk.client;

import com.yzk.model.product.Category;
import com.yzk.model.product.SkuInfo;
import com.yzk.vo.product.SkuInfoVo;
import com.yzk.vo.product.SkuStockLockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @GetMapping("/api/product/inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") Long categoryId);

    @GetMapping("/api/product/inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable Long skuId);

    @PostMapping("/api/product/inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList);

    //@ApiOperation(value = "根据关键字获取sku列表")
    @GetMapping("/api/product/inner/findSkuInfoByKeyword/{keyword}")
    public List<SkuInfo> findSkuInfoByKeyword(@PathVariable("keyword") String keyword);
<<<<<<< HEAD
    //修改sku状态
    @PutMapping("/api/product/inner/updateSkuType/{skuIdList}/{skuType}")
    public void updateSkuType(@PathVariable List<Long> skuIdList, @PathVariable SkuType skuType);
    //根据skuId 获取skuInfoVo
    @GetMapping("/api/product/inner/getSkuInfoVo/{skuId}")
    SkuInfoVo findSkuInfoBySku(@PathVariable("skuId") Long skuId);
=======
>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)
}
