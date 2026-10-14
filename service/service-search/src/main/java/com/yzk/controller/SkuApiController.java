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
}