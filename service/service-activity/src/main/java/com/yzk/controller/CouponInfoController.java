package com.yzk.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yzk.common.result.Result;
import com.yzk.model.activity.CouponInfo;
import com.yzk.service.CouponInfoService;
import com.yzk.vo.activity.CouponRuleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 优惠券信息 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-07
 */
@Api(tags="优惠券信息管理")
@RestController
@RequestMapping("/admin/activity/couponInfo")
//@CrossOrigin
public class CouponInfoController {
    @Autowired
    private CouponInfoService couponInfoService;



    @ApiOperation("优惠劵新增")
    @PostMapping("save")
    public Result save(@RequestBody CouponInfo couponInfo) {
        couponInfoService.save(couponInfo);
        return Result.ok(null);
    }
    @ApiOperation("优惠劵删除")
    @DeleteMapping ("/remove/{id}")
    public Result delete(@PathVariable Long id) {
        couponInfoService.removeCouponById(id);
        return Result.ok(null);
    }
    @ApiOperation("优惠劵修改")
    @PutMapping ("update")
    public Result update(@RequestBody CouponInfo couponInfo) {
        couponInfoService.updateById(couponInfo);
        return Result.ok(null);
    }
    @ApiOperation("根据id获取优惠劵")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        CouponInfo couponInfo = couponInfoService.getCouponInfo(id);
        return Result.ok(couponInfo);
    }
    @ApiOperation("分页获取优惠劵列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit) {
        IPage<CouponInfo> pageModel =
                couponInfoService.selectPageCouponInfo(page,limit);
        return Result.ok(pageModel);
    }
    @ApiOperation("查询优惠劵规则列表")
    @GetMapping("findCouponRuleList/{id}")
    public Result findCouponRuleList(@PathVariable Long id) {
        Map<String,Object> map =
                couponInfoService.findCouponRuleList(id);
        return Result.ok(map);
    }

    @ApiOperation("优惠劵规则新增")
    @PostMapping("saveCouponRule")
    public Result saveCouponRule(@RequestBody CouponRuleVo couponRuleVo) {
        couponInfoService.saveCouponRule(couponRuleVo);
        return Result.ok(null);
    }
}

