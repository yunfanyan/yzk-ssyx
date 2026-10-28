<<<<<<< HEAD
package com.yzk.controller;


import com.yzk.common.auth.AuthContextHolder;
import com.yzk.common.result.Result;
import com.yzk.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "商品详情")
@RestController
@RequestMapping("api/home")
public class ItemApiController {
    @Autowired
    private ItemService itemService;
    @ApiOperation("商品详情")
    @GetMapping("item/{id}")
    public Result index(@PathVariable Long id) {
        Long userId = AuthContextHolder.getUserId();
        Map<String,Object> map = itemService.item(id,userId);
        return Result.ok(map);
    }
}
=======
>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)
