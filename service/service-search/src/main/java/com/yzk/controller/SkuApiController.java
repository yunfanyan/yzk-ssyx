package com.yzk.controller;



import com.yzk.common.result.Result;
import com.yzk.model.search.SkuEs;
import com.yzk.service.SkuService;
import com.yzk.vo.search.SkuEsQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description: es-sku控制器
 * @date: 2026-10-08 00:32
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Api(tags="搜索模块管理")
@RestController
@RequestMapping("/api/search/sku")
public class SkuApiController {
    @Autowired
    private SkuService skuService;
    /**
     * 上架
     * @param skuId
     * @return
     */
    @ApiOperation("商品上架")
    @GetMapping("inner/upperSku/{skuId}")
    public Result upperSku(@PathVariable Long skuId) {
        skuService.upperSku(skuId);
        return Result.ok(null);
    }

    /**
     * 下架
     * @param skuId
     * @return
     */
    @ApiOperation("商品下架架")
    @GetMapping("inner/lowerSku/{skuId}")
    public Result lowerSku(@PathVariable Long skuId) {
        skuService.lowerSku(skuId);
        return Result.ok(null);
    }
<<<<<<< HEAD
    @ApiOperation("搜索热门商品")
    @GetMapping("inner/findHotSkuList")
    public List<SkuEs> findHotSkuList(){
       return skuService.findHotSkuList();
    }

    @ApiOperation(value = "搜索商品")
    @GetMapping("{page}/{limit}")
    public Result list(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Integer page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Integer limit,
            @ApiParam(name = "searchParamVo", value = "查询对象", required = false)
            SkuEsQueryVo searchParamVo) {

        Pageable pageable = PageRequest.of(page-1, limit);
        Page<SkuEs> pageModel =  skuService.search(pageable, searchParamVo);
        return Result.ok(pageModel);
    }
    @ApiOperation(value = "更新商品热度")
    @GetMapping("inner/incrHotScore/{skuId}")
    public Boolean incrHotScore(@PathVariable("skuId") Long skuId){
       return skuService.incrHotScore(skuId);
    }

=======
>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)
}