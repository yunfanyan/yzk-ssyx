package com.yzk.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzk.common.result.Result;
import com.yzk.model.product.SkuInfo;
import com.yzk.service.SkuInfoService;
import com.yzk.vo.product.SkuInfoQueryVo;
import com.yzk.vo.product.SkuInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
@Api(tags="sku信息管理")
@RestController
@RequestMapping("/admin/product/skuInfo")
//@CrossOrigin
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;


    @ApiOperation("添加商品sku信息")
    @PostMapping("save")
    public Result save(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.saveSkuInfo(skuInfoVo);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        skuInfoService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        skuInfoService.removeByIds(idList);
        return Result.ok(null);
    }
    @ApiOperation("修改sku")
    @PutMapping("update")
    public Result update(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.updateSkuInfo(skuInfoVo);
        return Result.ok(null);
    }
    @ApiOperation("获取sku信息")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SkuInfoVo skuInfoVo = skuInfoService.getSkuInfo(id);
        return Result.ok(skuInfoVo);
    }

    @ApiOperation("sku列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       SkuInfoQueryVo skuInfoQueryVo) {
        Page<SkuInfo> pageParam = new Page<>(page,limit);
        IPage<SkuInfo> pageModel =
                skuInfoService.selectPageSkuInfo(pageParam,skuInfoQueryVo);
        return Result.ok(pageModel);
    }


    @ApiOperation("商品审核")
    @GetMapping("check/{skuId}/{status}")
    public Result check(@PathVariable Long skuId,
                        @PathVariable Integer status) {
        skuInfoService.check(skuId,status);
        return Result.ok(null);
    }


    @ApiOperation("商品上下架")
    @GetMapping("publish/{skuId}/{status}")
    public Result publish(@PathVariable Long skuId,
                          @PathVariable Integer status) {
        skuInfoService.publish(skuId,status);
        return Result.ok(null);
    }


    @ApiOperation("新人专享")
    @GetMapping("isNewPerson/{skuId}/{status}")
    public Result isNewPerson(@PathVariable("skuId") Long skuId,
                              @PathVariable("status") Integer status) {
        skuInfoService.isNewPerson(skuId, status);
        return Result.ok(null);
    }
}

