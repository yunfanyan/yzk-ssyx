package com.yzk.controller;

import com.yzk.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 系统登录
 * @date: 2026-10-04 19:11
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Api(tags = "登录接口")
@RestController
@RequestMapping("/admin/acl/index")
@CrossOrigin
public class IndexController {
    @ApiOperation("登录")
    @PostMapping("login")
    public Result login() {
        //返回token值
        Map<String,String> map = new HashMap<>();
        map.put("token","token-admin");
        return Result.ok(map);
    }

    //    url: '/admin/acl/index/info',
//    method: 'get',
    //2 getInfo 获取信息
    @ApiOperation("获取信息")
    @GetMapping("info")
    public Result info() {
        Map<String,String> map = new HashMap<>();
        map.put("name","admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

    //    url: '/admin/acl/index/logout',
//    method: 'post'
    //3 logout 退出
    @ApiOperation("退出")
    @PostMapping("logout")
    public Result logout() {
        return Result.ok(null);
    }
}