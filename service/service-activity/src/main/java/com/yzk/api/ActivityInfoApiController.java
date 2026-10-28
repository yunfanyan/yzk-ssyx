<<<<<<< HEAD
package com.yzk.api;


import com.yzk.model.order.CartInfo;
import com.yzk.service.ActivityInfoService;
import com.yzk.vo.order.OrderConfirmVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activity")
public class ActivityInfoApiController {
    @Autowired
    private ActivityInfoService activityInfoService;

    //获取购物车里面满足条件优惠卷和活动的信息
    @ApiOperation("获取购物车里面满足条件优惠卷和活动的信息")
    @PostMapping("inner/findCartActivityAndCoupon/{userId}")
    public OrderConfirmVo findCartActivityAndCoupon(@RequestBody List<CartInfo> cartInfoList,
                                                    @PathVariable("userId") Long userId){
        return activityInfoService.finnCartActivityAndCoupon(cartInfoList,userId);

    }

    @ApiOperation(value = "根据skuId列表获取促销信息")
    @PostMapping("inner/findActivity")
    public Map<Long, List<String>> findActivity(@RequestBody List<Long> skuIdList){
        return activityInfoService.findActivity(skuIdList);
    }
    @ApiOperation("根据skuID获取营销数据和优惠卷")
    @GetMapping("inner/findActivityAndCoupon/{skuId}/{userId}")
    public Map<String,Object> findActivityAndCoupon(@PathVariable("skuId") Long skuId,
                                                    @PathVariable("userId") Long userId){
        return activityInfoService.findActivityAndCoupon(skuId,userId);
    }
}
=======
>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)
