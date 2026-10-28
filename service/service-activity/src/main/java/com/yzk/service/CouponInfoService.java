package com.yzk.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.activity.CouponInfo;
<<<<<<< HEAD
import com.yzk.model.order.CartInfo;
import com.yzk.vo.activity.CouponRuleVo;

import java.util.List;
import java.util.Map;
=======

>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)


/**
 * <p>
 * 优惠券信息 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-07
 */
public interface CouponInfoService extends IService<CouponInfo> {

    /**
     * 获取优惠劵列表
     * @param skuId
     * @param userId
     * @return
     */
    List<CouponInfo> findCouponInfo(Long skuId, Long userId);

    /**
     * 获取购物项中使用优惠劵的sku
     * @param cartInfoList
     * @param userId
     * @return
     */
    List<CouponInfo> findCartCouponInfo(List<CartInfo> cartInfoList, Long userId);
}
