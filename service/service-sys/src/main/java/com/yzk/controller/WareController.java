package com.yzk.controller;

import com.yzk.common.result.Result;
import com.yzk.model.sys.Ware;
import com.yzk.service.WareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 仓库控制器
 * @date: 2026-10-05 22:28
 * @version: 1.0
 * @email: yzk@qq.com
 */

@Api(tags = "仓库接口")
@RestController
@RequestMapping("/admin/sys/ware")
//@CrossOrigin
public class WareController {
    @Autowired
    private WareService wareService;


    @ApiOperation("查询所有仓库列表")
    @GetMapping("findAllList")
    public Result findAllList() {
        List<Ware> list = wareService.list();
        return Result.ok(list);
    }

}