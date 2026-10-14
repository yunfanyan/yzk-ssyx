package com.yzk.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.client.ProductFeignClient;
import com.yzk.enums.CouponRangeType;
import com.yzk.mapper.CouponInfoMapper;
import com.yzk.mapper.CouponRangeMapper;
import com.yzk.mapper.CouponUseMapper;
import com.yzk.model.activity.CouponInfo;
import com.yzk.model.activity.CouponRange;
import com.yzk.model.product.Category;
import com.yzk.model.product.SkuInfo;
import com.yzk.service.CouponInfoService;
import com.yzk.vo.activity.CouponRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.awt.font.NumericShaper;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券信息 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-07
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {
    @Autowired
    private CouponRangeMapper couponRangeMapper;
    @Autowired
    private ProductFeignClient productFeignClient;

    /**
     * 删除优惠劵
     */
    @Override
    public void removeCouponById(Long couponInfoId) {
        //删除优惠劵范围
        couponRangeMapper.delete(new LambdaQueryWrapper<CouponRange>().eq(CouponRange::getCouponId,couponInfoId));
        //删除优惠劵
        baseMapper.deleteById(couponInfoId);
    }

    /**
     * 根据id查询优惠劵信息
     * @param id
     * @return
     */
    @Override
    public CouponInfo getCouponInfo(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 分页查询优惠劵信息
     * @param page
     * @param limit
     * @return
     */
    @Override
    public IPage<CouponInfo> selectPageCouponInfo(Long page, Long limit) {
        Page<CouponInfo> pageParam = new Page<>(page, limit);
        return baseMapper.selectPage(pageParam, null);
    }

    @Override
    public Map<String, Object> findCouponRuleList(Long id) {
    //查询优惠劵是否存在
        CouponInfo couponInfo = baseMapper.selectById(id);
        if(couponInfo==null){
            return new HashMap<>();
        }
        //查询rang_id
        List<CouponRange> couponRanges = couponRangeMapper.selectList(new LambdaQueryWrapper<CouponRange>().eq(CouponRange::getCouponId, id));
        List<Long> collect = couponRanges.stream().map(CouponRange::getRangeId).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(collect)){
            return new HashMap<>();
        }
        Map<String,Object> result = new HashMap();
        //返回规则列表
        if(couponInfo.getRangeType()== CouponRangeType.SKU){
            List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(collect);
            result.put("skuInfoList",skuInfoList);
        }else if(couponInfo.getRangeType()== CouponRangeType.CATEGORY) {
            List<Category> categoryList = productFeignClient.findCategoryList(collect);
            result.put("categoryList",categoryList);
        }
        return result;

    }

    /**
     * 保存规则
     * @param couponRuleVo
     */
    @Override
    public void saveCouponRule(CouponRuleVo couponRuleVo) {
        //根据优惠卷id删除规则数据
        couponRangeMapper.delete(
                new LambdaQueryWrapper<CouponRange>().eq(CouponRange::getCouponId,couponRuleVo.getCouponId())
        );

        //更新优惠卷基本信息
        CouponInfo couponInfo = baseMapper.selectById(couponRuleVo.getCouponId());
        couponInfo.setRangeType(couponRuleVo.getRangeType());
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount());
        couponInfo.setAmount(couponRuleVo.getAmount());
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount());
        couponInfo.setRangeDesc(couponRuleVo.getRangeDesc());
        baseMapper.updateById(couponInfo);

        //添加优惠卷新规则数据
        List<CouponRange> couponRangeList = couponRuleVo.getCouponRangeList();
        for (CouponRange couponRange:couponRangeList) {
            //设置优惠卷id
            couponRange.setCouponId(couponRuleVo.getCouponId());
            //添加
            couponRangeMapper.insert(couponRange);
        }
    }
}
