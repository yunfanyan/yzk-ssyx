package com.yzk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzk.common.result.Result;
import com.yzk.model.sys.RegionWare;
import com.yzk.service.RegionWareService;
import com.yzk.vo.sys.RegionWareQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 区域仓库控制器
 * @date: 2026-10-05 23:25
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Api(tags = "开通区域接口")
@RestController
@RequestMapping("/admin/sys/regionWare")
//@CrossOrigin
public class RegionWareController {
    @Autowired
    private RegionWareService regionWareService;


    @ApiOperation("添加开通区域")
    @PostMapping("save")
    public Result addRegionWare(@RequestBody RegionWare regionWare) {
        regionWareService.saveRegionWare(regionWare);
        return Result.ok(null);
    }


    @ApiOperation("删除开通区域")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        regionWareService.removeById(id);
        return Result.ok(null);
    }


    @ApiOperation("取消开通区域")
    @PostMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,
                               @PathVariable Integer status) {
        regionWareService.updateStatus(id,status);
        return Result.ok(null);
    }
    @ApiOperation("开通区域列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       RegionWareQueryVo regionWareQueryVo) {
        Page<RegionWare> pageParam = new Page<>(page,limit);
        IPage<RegionWare> pageModel = regionWareService.selectPageRegionWare(pageParam,regionWareQueryVo);
        return Result.ok(pageModel);
    }
}