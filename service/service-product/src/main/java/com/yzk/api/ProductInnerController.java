package com.yzk.api;



import com.yzk.enums.SkuType;
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
     * 获取新人专享商品
     * @return
     */
    @GetMapping("inner/findNewPersonSkuInfoList")
    public List<SkuInfo> findNewPersonSkuInfoList(){
       return skuInfoService.findNewPersonSkuInfoList();
    }

    /**
     * 获取所有的商品分类
     * @return
     */
    @GetMapping("inner/findAllCategoryList")
    public List<Category> findAllCategoryList(){
        return categoryService.getBaseMapper().selectList(null);
    }

    /**
     * 修改sku状态
     * @param skuIdList
     * @param skuType
     */
    @PutMapping("inner/updateSkuType/{skuIdList}/{skuType}")
    public void updateSkuType(@PathVariable List<Long> skuIdList, @PathVariable SkuType skuType){
        skuInfoService.updateSkutypes( skuIdList,  skuType);
    }
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
    //根据分类id获取分类信息
    @GetMapping("inner/getCategory/{categoryId}")
    public Category  getCategory(@PathVariable Long categoryId) {
        Category category = categoryService.getById(categoryId);
        return category;
    }
    //根据分类id获取分类列表
    @PostMapping("inner/findCategoryList")
    public List<Category> findCategoryList(@RequestBody List<Long> categoryIdList) {
        return categoryService.listByIds(categoryIdList);
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
