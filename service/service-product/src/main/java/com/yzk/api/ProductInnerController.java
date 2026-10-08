package com.yzk.api;



import com.yzk.model.product.Category;
import com.yzk.model.product.SkuInfo;
import com.yzk.service.CategoryService;
import com.yzk.service.SkuInfoService;
import com.yzk.vo.product.SkuInfoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductInnerController {
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private CategoryService categoryService;


    /**
     *根据skuId获取sku信息
     * @param skuId
     * @return
     */
    @GetMapping("inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable Long skuId){
        SkuInfo skuInfo = skuInfoService.getById(skuId);
        return skuInfo;
    }

    /**
     * 后期sku分组信息
     * @param categoryId
     * @return
     */
    @GetMapping("inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") Long categoryId){
        return categoryService.getById(categoryId);
    }

    //@ApiOperation(value = "批量获取sku信息")
    @PostMapping("inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList) {
        return skuInfoService.findSkuInfoList(skuIdList);
    }

    //@ApiOperation(value = "根据关键字获取sku列表")
    @GetMapping("inner/findSkuInfoByKeyword/{keyword}")
    public List<SkuInfo> findSkuInfoByKeyword(@PathVariable("keyword") String keyword) {
        return skuInfoService.findSkuInfoByKeyword(keyword);
    }



}
