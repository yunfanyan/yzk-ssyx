package com.yzk.controller;

import com.yzk.common.result.Result;
import com.yzk.model.sys.Region;
import com.yzk.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 区域控制器
 * @date: 2026-10-05 11:58
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Api(tags = "区域管理")
@RestController
@RequestMapping("/admin/sys/region")
@CrossOrigin
public class RegionController {
    @Autowired
    private RegionService regionService;

    @ApiOperation("根据区域关键字查询区域列表信息")
    @GetMapping("findRegionByKeyword/{keyword}")
    public Result findRegionByKeyword(@PathVariable("keyword") String keyword) {
        List<Region> list = regionService.getRegionByKeyword(keyword);
        return Result.ok(list);
    }
}