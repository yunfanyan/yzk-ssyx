package com.yzk.api;



import com.yzk.model.product.Category;
import com.yzk.model.product.SkuInfo;
import com.yzk.service.CategoryService;
import com.yzk.service.SkuInfoService;
import com.yzk.vo.product.SkuInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
