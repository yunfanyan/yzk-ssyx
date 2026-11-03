package com.yzk.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.client.ProductFeignClient;
import com.yzk.enums.ActivityType;
import com.yzk.mapper.ActivityInfoMapper;
import com.yzk.mapper.ActivityRuleMapper;
import com.yzk.mapper.ActivitySkuMapper;
import com.yzk.model.activity.ActivityInfo;
import com.yzk.model.activity.ActivityRule;
import com.yzk.model.activity.ActivitySku;
import com.yzk.model.activity.CouponInfo;
import com.yzk.model.order.CartInfo;
import com.yzk.model.product.SkuInfo;
import com.yzk.service.ActivityInfoService;
import com.yzk.service.CouponInfoService;
import com.yzk.vo.activity.ActivityRuleVo;
import com.yzk.vo.order.CartInfoVo;
import com.yzk.vo.order.OrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-07
 */
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo> implements ActivityInfoService {
    @Autowired
    private ActivityRuleMapper activityRuleMapper;
    @Autowired
    private ActivitySkuMapper activitySkuMapper;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private ActivityInfoMapper activityInfoMapper;
    @Autowired
    private CouponInfoService couponInfoService;

    /**
     * 根据ID删除活动
     * @param id
     */
    @Override
    public void removeActivity(Long id) {
        //删除活动sku
        activitySkuMapper.delete(new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, id));
        //删除活动规则
        activityRuleMapper.delete(new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId,id));
        //删除活动
        baseMapper.deleteById(id);
    }

    /**
     * 条件查询
     * @param pageParam 分页条件
     * @return
     */
    @Override
    public IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam) {
        IPage<ActivityInfo> activityInfoList = baseMapper.selectPage(pageParam, null);
        List<ActivityInfo> records = activityInfoList.getRecords();
        records.stream().forEach(item->{
            item.setActivityTypeString(item.getActivityType().getComment());
        });
        return activityInfoList;
    }

    /**
     * 活动规则新增
     * @param activityRuleVo 活动规则参数
     */
    @Override
    public void saveActivityRule(ActivityRuleVo activityRuleVo) {
        ActivityInfo activityInfo = baseMapper.selectById(activityRuleVo.getActivityId());
        if(activityInfo==null){
            return;
        }
        //1.删除之前的活动
        activityRuleMapper.delete(new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId,activityRuleVo.getActivityId()));
        //2.新增活动
        for(ActivityRule activityRule:activityRuleVo.getActivityRuleList()){
            activityRuleMapper.insert(activityRule);
        }
        //3.删除之前的活动sku
        activitySkuMapper.delete(new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, activityRuleVo.getActivityId()));
        //4.新增活动sku
        for(ActivitySku activitySku:activityRuleVo.getActivitySkuList()){
            activitySku.setActivityId(activityInfo.getId());
            activitySkuMapper.insert(activitySku);
        }


    }

    /**
     * 查询活动规则列表
     * @param id 活动ID
     * @return
     */
    @Override
    public Map<String, Object> findActivityRuleList(Long id) {
        //1.通过id查询规则信息列表
        List<ActivityRule> activityRules = activityRuleMapper.selectList(new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId, id));
        Map<String,Object> map = new HashMap();
        map.put("activityRuleList",activityRules);
        //2.获取商品sku列表
        //获取sku id
        List<ActivitySku> activitySkus = activitySkuMapper.selectList(new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, id));
        List<Long> skuIds = activitySkus.stream().map(ActivitySku::getSkuId).collect(Collectors.toList());
        List<SkuInfo> skuInfoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(skuIds)){
            skuInfoList = productFeignClient.findSkuInfoList(skuIds);
        }

        map.put("skuInfoList",skuInfoList);

        return map;
    }

    /**
     * 模糊查询sku
     * @param keyword 查询key
     * @return
     */
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        //1.查询符合条件的sku
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoByKeyword(keyword);
        if(skuInfoList==null){
            return skuInfoList;
        }

        //2.查询还在活动中的sku-id
        List<Long> skuIds = skuInfoList.stream().map(SkuInfo::getId).collect(Collectors.toList());
        List<Long> skuList =  activityInfoMapper.findActivityIdIsExist(skuIds);
        //3，查询不再活动中的sku
        ArrayList<SkuInfo> findSkuList = new ArrayList<>();

        for(SkuInfo skuInfo:skuInfoList){
            if(!skuList.contains(skuInfo.getId())){
                findSkuList.add(skuInfo);
            }

        }
        return findSkuList;
    }

    /**
     * 获取所有商品促销活动信息
     * @param skuIdList
     * @return
     */
    @Override
    public Map<Long, List<String>> findActivity(List<Long> skuIdList) {
        Map<Long, List<String>> result = new HashMap<>();
        //skuIdList遍历，得到每个skuId
        skuIdList.forEach(skuId -> {
            //根据skuId进行查询，查询sku对应活动里面规则列表
            List<ActivityRule> activityRuleList =
                    baseMapper.findActivityRule(skuId);
            //数据封装，规则名称
            if(!CollectionUtils.isEmpty(activityRuleList)) {
                List<String> ruleList = new ArrayList<>();
                //把规则名称处理
                for (ActivityRule activityRule:activityRuleList) {
                    ruleList.add(this.getRuleDesc(activityRule));
                }
                result.put(skuId,ruleList);
            }
        });
        return result;
    }

    @Override
    public OrderConfirmVo findCartActivityAndCoupon(List<CartInfo> cartInfoList, Long userId) {
        //1 获取购物车，每个购物项参与活动，根据活动规则分组，
        //一个规则对应多个商品
        List<CartInfoVo> cartInfoVoList = this.findCartActivityList(cartInfoList);

        //2 计算参与活动之后金额
        BigDecimal activityReduceAmount = cartInfoVoList.stream()
                .filter(cartInfoVo -> cartInfoVo.getActivityRule() != null)
                .map(cartInfoVo -> cartInfoVo.getActivityRule().getReduceAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //3 获取购物车可以使用优惠卷列表
        List<CouponInfo> couponInfoList =
                couponInfoService.findCartCouponInfo(cartInfoList,userId);

        //4 计算商品使用优惠卷之后金额，一次只能使用一张优惠卷
        BigDecimal couponReduceAmount = new BigDecimal(0);
        if(!CollectionUtils.isEmpty(couponInfoList)) {
            couponReduceAmount = couponInfoList.stream()
                    .filter(couponInfo -> couponInfo.getIsOptimal().intValue() == 1)
                    .map(couponInfo -> couponInfo.getAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        //5 计算没有参与活动，没有使用优惠卷原始金额
        BigDecimal originalTotalAmount = cartInfoList.stream()
                .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                .map(cartInfo -> cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //6 最终金额
        BigDecimal totalAmount =
                originalTotalAmount.subtract(activityReduceAmount).subtract(couponReduceAmount);

        //7 封装需要数据到OrderConfirmVo,返回
        OrderConfirmVo orderTradeVo = new OrderConfirmVo();
        orderTradeVo.setCarInfoVoList(cartInfoVoList);
        orderTradeVo.setActivityReduceAmount(activityReduceAmount);
        orderTradeVo.setCouponInfoList(couponInfoList);
        orderTradeVo.setCouponReduceAmount(couponReduceAmount);
        orderTradeVo.setOriginalTotalAmount(originalTotalAmount);
        orderTradeVo.setTotalAmount(totalAmount);
        return orderTradeVo;
    }


    //获取购物车对应规则数据

    public List<CartInfoVo> findCartActivityList(List<CartInfo> cartInfoList) {
        //创建最终返回集合
        List<CartInfoVo> cartInfoVoList = new ArrayList<>();
        //获取所有skuId
        List<Long> skuIdList = cartInfoList.stream().map(CartInfo::getSkuId).collect(Collectors.toList());
        //根据所有skuId列表获取参与活动
        List<ActivitySku> activitySkuList = baseMapper.selectCartActivity(skuIdList);
        //根据活动进行分组，每个活动里面有哪些skuId信息
        //map里面key是分组字段 活动id
        // value是每组里面sku列表数据，set集合
        Map<Long, Set<Long>> activityIdToSkuIdListMap = activitySkuList.stream()
                .collect(
                        Collectors.groupingBy(
                                ActivitySku::getActivityId,
                                Collectors.mapping(ActivitySku::getSkuId, Collectors.toSet())
                        )
                );

        //获取活动里面规则数据
        //key是活动id  value是活动里面规则列表数据
        Map<Long,List<ActivityRule>> activityIdToActivityRuleListMap
                = new HashMap<>();
        //所有活动id
        Set<Long> activityIdSet = activitySkuList.stream().map(ActivitySku::getActivityId)
                .collect(Collectors.toSet());
        if(!CollectionUtils.isEmpty(activityIdSet)) {
            //activity_rule表
            LambdaQueryWrapper<ActivityRule> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(ActivityRule::getConditionAmount,ActivityRule::getConditionNum);
            wrapper.in(ActivityRule::getActivityId,activityIdSet);
            List<ActivityRule> activityRuleList = activityRuleMapper.selectList(wrapper);

            //封装到activityIdToActivityRuleListMap里面
            //根据活动id进行分组
            activityIdToActivityRuleListMap = activityRuleList.stream().collect(
                    Collectors.groupingBy(activityRule -> activityRule.getActivityId())
            );
        }

        //有活动的购物项skuId
        Set<Long> activitySkuIdSet = new HashSet<>();
        if(!CollectionUtils.isEmpty(activityIdToSkuIdListMap)) {
            //遍历activityIdToSkuIdListMap集合
            Iterator<Map.Entry<Long, Set<Long>>> iterator = activityIdToSkuIdListMap.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry<Long, Set<Long>> entry = iterator.next();
                //活动id
                Long activityId = entry.getKey();
                //每个活动对应skuId列表
                Set<Long> currentActivitySkuIdSet = entry.getValue();
                //获取当前活动对应的购物项列表
                List<CartInfo> currentActivityCartInfoList = cartInfoList.stream()
                        .filter(cartInfo ->
                                currentActivitySkuIdSet.contains(cartInfo.getSkuId())).collect(Collectors.toList());
                //计数购物项总金额和总数量
                BigDecimal activityTotalAmount =
                        this.computeTotalAmount(currentActivityCartInfoList);
                int activityTotalNum = this.computeCartNum(currentActivityCartInfoList);

                //计算活动对应规则
                //根据activityId获取活动对应规则
                List<ActivityRule> currentActivityRuleList =
                        activityIdToActivityRuleListMap.get(activityId);
                ActivityType activityType = currentActivityRuleList.get(0).getActivityType();
                //判断活动类型：满减和打折
                ActivityRule activityRule = null;
                if(activityType == ActivityType.FULL_REDUCTION) {//满减"
                    activityRule = this.computeFullReduction(activityTotalAmount, currentActivityRuleList);
                } else {//满量
                    activityRule = this.computeFullDiscount(activityTotalNum, activityTotalAmount, currentActivityRuleList);
                }

                //CartInfoVo封装
                CartInfoVo cartInfoVo = new CartInfoVo();
                cartInfoVo.setActivityRule(activityRule);
                cartInfoVo.setCartInfoList(currentActivityCartInfoList);
                cartInfoVoList.add(cartInfoVo);

                //记录哪些购物项参与活动
                activitySkuIdSet.addAll(currentActivitySkuIdSet);
            }
        }

        //没有活动购物项skuId
        //获取哪些skuId没有参加活动
        skuIdList.removeAll(activitySkuIdSet);
        if(!CollectionUtils.isEmpty(skuIdList)) {
            //skuId对应购物项
            Map<Long, CartInfo> skuIdCartInfoMap = cartInfoList.stream().collect(
                    Collectors.toMap(CartInfo::getSkuId, CartInfo -> CartInfo)
            );
            for(Long skuId  : skuIdList) {
                CartInfoVo cartInfoVo = new CartInfoVo();
                cartInfoVo.setActivityRule(null);//没有活动

                List<CartInfo> cartInfos = new ArrayList<>();
                cartInfos.add(skuIdCartInfoMap.get(skuId));
                cartInfoVo.setCartInfoList(cartInfos);

                cartInfoVoList.add(cartInfoVo);
            }
        }

        return cartInfoVoList;
    }


    //构造规则名称的方法
    private String getRuleDesc(ActivityRule activityRule) {
        ActivityType activityType = activityRule.getActivityType();
        StringBuffer ruleDesc = new StringBuffer();
        if (activityType == ActivityType.FULL_REDUCTION) {
            ruleDesc
                    .append("满")
                    .append(activityRule.getConditionAmount())
                    .append("元减")
                    .append(activityRule.getBenefitAmount())
                    .append("元");
        } else {
            ruleDesc
                    .append("满")
                    .append(activityRule.getConditionNum())
                    .append("元打")
                    .append(activityRule.getBenefitDiscount())
                    .append("折");
        }
        return ruleDesc.toString();
    }
    /**
     * 计算满量打折最优规则
     * @param totalNum
     * @param activityRuleList //该活动规则skuActivityRuleList数据，已经按照优惠折扣从大到小排序了
     */
    private ActivityRule computeFullDiscount(Integer totalNum, BigDecimal totalAmount, List<ActivityRule> activityRuleList) {
        ActivityRule optimalActivityRule = null;
        //该活动规则skuActivityRuleList数据，已经按照优惠金额从大到小排序了
        for (ActivityRule activityRule : activityRuleList) {
            //如果订单项购买个数大于等于满减件数，则优化打折
            if (totalNum.intValue() >= activityRule.getConditionNum()) {
                BigDecimal skuDiscountTotalAmount = totalAmount.multiply(activityRule.getBenefitDiscount().divide(new BigDecimal("10")));
                BigDecimal reduceAmount = totalAmount.subtract(skuDiscountTotalAmount);
                activityRule.setReduceAmount(reduceAmount);
                optimalActivityRule = activityRule;
                break;
            }
        }
        if(null == optimalActivityRule) {
            //如果没有满足条件的取最小满足条件的一项
            optimalActivityRule = activityRuleList.get(activityRuleList.size()-1);
            optimalActivityRule.setReduceAmount(new BigDecimal("0"));
            optimalActivityRule.setSelectType(1);

            StringBuffer ruleDesc = new StringBuffer()
                    .append("满")
                    .append(optimalActivityRule.getConditionNum())
                    .append("元打")
                    .append(optimalActivityRule.getBenefitDiscount())
                    .append("折，还差")
                    .append(totalNum-optimalActivityRule.getConditionNum())
                    .append("件");
            optimalActivityRule.setRuleDesc(ruleDesc.toString());
        } else {
            StringBuffer ruleDesc = new StringBuffer()
                    .append("满")
                    .append(optimalActivityRule.getConditionNum())
                    .append("元打")
                    .append(optimalActivityRule.getBenefitDiscount())
                    .append("折，已减")
                    .append(optimalActivityRule.getReduceAmount())
                    .append("元");
            optimalActivityRule.setRuleDesc(ruleDesc.toString());
            optimalActivityRule.setSelectType(2);
        }
        return optimalActivityRule;
    }

    /**
     * 计算满减最优规则
     * @param totalAmount
     * @param activityRuleList //该活动规则skuActivityRuleList数据，已经按照优惠金额从大到小排序了
     */
    private ActivityRule computeFullReduction(BigDecimal totalAmount, List<ActivityRule> activityRuleList) {
        ActivityRule optimalActivityRule = null;
        //该活动规则skuActivityRuleList数据，已经按照优惠金额从大到小排序了
        for (ActivityRule activityRule : activityRuleList) {
            //如果订单项金额大于等于满减金额，则优惠金额
            if (totalAmount.compareTo(activityRule.getConditionAmount()) > -1) {
                //优惠后减少金额
                activityRule.setReduceAmount(activityRule.getBenefitAmount());
                optimalActivityRule = activityRule;
                break;
            }
        }
        if(null == optimalActivityRule) {
            //如果没有满足条件的取最小满足条件的一项
            optimalActivityRule = activityRuleList.get(activityRuleList.size()-1);
            optimalActivityRule.setReduceAmount(new BigDecimal("0"));
            optimalActivityRule.setSelectType(1);

            StringBuffer ruleDesc = new StringBuffer()
                    .append("满")
                    .append(optimalActivityRule.getConditionAmount())
                    .append("元减")
                    .append(optimalActivityRule.getBenefitAmount())
                    .append("元，还差")
                    .append(totalAmount.subtract(optimalActivityRule.getConditionAmount()))
                    .append("元");
            optimalActivityRule.setRuleDesc(ruleDesc.toString());
        } else {
            StringBuffer ruleDesc = new StringBuffer()
                    .append("满")
                    .append(optimalActivityRule.getConditionAmount())
                    .append("元减")
                    .append(optimalActivityRule.getBenefitAmount())
                    .append("元，已减")
                    .append(optimalActivityRule.getReduceAmount())
                    .append("元");
            optimalActivityRule.setRuleDesc(ruleDesc.toString());
            optimalActivityRule.setSelectType(2);
        }
        return optimalActivityRule;
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

    private int computeCartNum(List<CartInfo> cartInfoList) {
        int total = 0;
        for (CartInfo cartInfo : cartInfoList) {
            //是否选中
            if(cartInfo.getIsChecked().intValue() == 1) {
                total += cartInfo.getSkuNum();
            }
        }
        return total;
    }
}
