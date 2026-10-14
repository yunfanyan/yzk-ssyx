package com.yzk.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.activity.CouponInfo;
import com.yzk.vo.activity.CouponRuleVo;

import java.util.Map;


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
     * 删除优惠劵信息
     * @param couponInfoId
     */
    void removeCouponById(Long couponInfoId);

    /**
     * 根据id查询优惠劵信息
     * @param id
     * @return
     */
    CouponInfo getCouponInfo(Long id);

    /**
     * 分页查询优惠劵信息
     * @param page
     * @param limit
     * @return
     */
    IPage<CouponInfo> selectPageCouponInfo(Long page, Long limit);

    /**
     * 获取优惠劵规则
     * @param id
     * @return
     */
    Map<String, Object> findCouponRuleList(Long id);

    /**
     * 保存优惠劵规则
     * @param couponRuleVo
     */
    void saveCouponRule(CouponRuleVo couponRuleVo);


}
