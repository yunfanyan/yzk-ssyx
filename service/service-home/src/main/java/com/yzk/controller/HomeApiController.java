package com.yzk.controller;


import com.yzk.common.auth.AuthContextHolder;
import com.yzk.common.result.Result;
import com.yzk.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * @description: 小程序首页控制器
 * @date: 2026-10-11 23:25
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Api(tags = "首页接口")
@RestController
@RequestMapping("api/home")
public class HomeApiController {
    @Autowired
    private HomeService homeService;

    @ApiOperation("首页数据显示接口")
    @GetMapping("index")
    public Result index(HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId();
        Map<String,Object> map = homeService.homeData(userId);
        return Result.ok(map);
    }

}
