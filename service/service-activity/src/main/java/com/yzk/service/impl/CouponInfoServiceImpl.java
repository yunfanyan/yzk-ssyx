package com.yzk.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.mapper.CouponInfoMapper;
import com.yzk.model.activity.CouponInfo;
<<<<<<< HEAD
import com.yzk.model.activity.CouponRange;
import com.yzk.model.order.CartInfo;
import com.yzk.model.product.Category;
import com.yzk.model.product.SkuInfo;
=======
>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)
import com.yzk.service.CouponInfoService;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import org.springframework.util.CollectionUtils;

import java.awt.font.NumericShaper;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
=======
>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)

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



<<<<<<< HEAD
    /**
     * 分页查询优惠劵信息
     * @param page
     * @param limit
     * @return
     */
    @Override
    public IPage<CouponInfo> selectPageCouponInfo(Long page, Long limit) {
        Page<CouponInfo> pageParam = new Page<>(page,limit);
        IPage<CouponInfo> couponInfoPage = baseMapper.selectPage(pageParam, null);
        List<CouponInfo> couponInfoList = couponInfoPage.getRecords();
        couponInfoList.stream().forEach(item -> {
            item.setCouponTypeString(item.getCouponType().getComment());
            CouponRangeType rangeType = item.getRangeType();
            if(rangeType != null) {
                item.setRangeTypeString(rangeType.getComment());
            }
        });
        return couponInfoPage;
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

    /**
     * 获取优惠劵信息列表
     * @param skuId
     * @param userId
     * @return
     */
    @Override
    public List<CouponInfo> findCouponInfo(Long skuId, Long userId) {
        //1.查询sku信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if(skuInfo==null){
            return Collections.emptyList();
        }
        return baseMapper.selectCouponInfoList(skuId,skuInfo.getCategoryId(),userId);
    }
    /**
     * 获取购物项中使用优惠劵的sku
     * @param cartInfoList
     * @param userId
     * @return
     */

    @Override
    public List<CouponInfo> findCartCouponInfo(List<CartInfo> cartInfoList, Long userId) {
        //1.获取用户所有的优惠劵信息列表
        List<CouponInfo> userAllCouponInfo = baseMapper.selectCartCouponInfoList(userId);
        if(CollectionUtils.isEmpty(userAllCouponInfo)){
            return new ArrayList<CouponInfo>();
        }

        //2.获取优惠劵id列表
        List<Long> couponInfoIdList = userAllCouponInfo.stream().map(CouponInfo::getId).collect(Collectors.toList());

        //3.查询优惠劵对应的范围
        List<CouponRange> couponRangeList = couponRangeMapper.selectList(new LambdaQueryWrapper<CouponRange>().in(CouponRange::getCouponId, couponInfoIdList));

        //4 获取优惠卷id 对应skuId列表
        //优惠卷id进行分组，得到map集合
        //     Map<Long,List<Long>>
        Map<Long,List<Long>> couponIdToSkuIdMap =
                this.findCouponIdToSkuIdMap(cartInfoList,couponRangeList);
        //5 遍历全部优惠卷集合，判断优惠卷类型
        //全场通用  sku和分类
        BigDecimal reduceAmount = new BigDecimal(0);
        CouponInfo optimalCouponInfo = null;
        for(CouponInfo couponInfo:userAllCouponInfo) {
            //全场通用
            if(CouponRangeType.ALL == couponInfo.getRangeType()) {
                //全场通用
                //判断是否满足优惠使用门槛
                //计算购物车商品的总价
                BigDecimal totalAmount = computeTotalAmount(cartInfoList);
                if(totalAmount.subtract(couponInfo.getConditionAmount()).doubleValue() >= 0){
                    couponInfo.setIsSelect(1);
                }
            } else {
                //优惠卷id获取对应skuId列表
                List<Long> skuIdList
                        = couponIdToSkuIdMap.get(couponInfo.getId());
                //满足使用范围购物项
                List<CartInfo> currentCartInfoList = cartInfoList.stream()
                        .filter(cartInfo -> skuIdList.contains(cartInfo.getSkuId()))
                        .collect(Collectors.toList());
                BigDecimal totalAmount = computeTotalAmount(currentCartInfoList);
                if(totalAmount.subtract(couponInfo.getConditionAmount()).doubleValue() >= 0){
                    couponInfo.setIsSelect(1);
                }
            }
            if (couponInfo.getIsSelect().intValue() == 1 && couponInfo.getAmount().subtract(reduceAmount).doubleValue() > 0) {
                reduceAmount = couponInfo.getAmount();
                optimalCouponInfo = couponInfo;
            }

        }
        //6 返回List<CouponInfo>
        if(null != optimalCouponInfo) {
            optimalCouponInfo.setIsOptimal(1);
        }
        return userAllCouponInfo;

    }
    private BigDecimal computeTotalAmount(List<CartInfo> cartInfoList) {
        BigDecimal total = new BigDecimal("0");
        for (CartInfo cartInfo : cartInfoList) {
            //是否选中
            if(cartInfo.getIsChecked().intValue() == 1) {
                BigDecimal itemTotal = cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum()));
                total = total.add(itemTotal);
            }
        }
        return total;
    }

    /**
     * 获取优惠卷id 对应skuId列表
     * @param cartInfoList
     * @param couponRangeList
     * @return
     */
    private Map<Long, List<Long>> findCouponIdToSkuIdMap(List<CartInfo> cartInfoList, List<CouponRange> couponRangeList) {
        Map<Long, List<Long>> couponIdToSkuIdMap = new HashMap<>();
        //couponRangeList数据处理，根据优惠卷id分组
        Map<Long, List<CouponRange>> couponRangeToRangeListMap = couponRangeList.stream().collect(Collectors.groupingBy(CouponRange::getCouponId));

        //遍历map集合
        Iterator<Map.Entry<Long, List<CouponRange>>> iterator =
                couponRangeToRangeListMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, List<CouponRange>> entry = iterator.next();
            Long couponId = entry.getKey();
            List<CouponRange> rangeList = entry.getValue();

            //创建集合 set
            Set<Long> skuIdSet = new HashSet<>();
            for (CartInfo cartInfo:cartInfoList) {
                for (CouponRange couponRange:rangeList) {
                    //判断
                    if(couponRange.getRangeType() == CouponRangeType.SKU
                            && couponRange.getRangeId().longValue() == cartInfo.getSkuId().longValue()) {
                        skuIdSet.add(cartInfo.getSkuId());
                    } else if(couponRange.getRangeType() == CouponRangeType.CATEGORY
                            && couponRange.getRangeId().longValue() == cartInfo.getCategoryId().longValue()) {
                        skuIdSet.add(cartInfo.getSkuId());
                    }
                }
            }
            couponIdToSkuIdMap.put(couponId,new ArrayList<>(skuIdSet));
        }
        return couponIdToSkuIdMap;
    }
=======
>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)
}
