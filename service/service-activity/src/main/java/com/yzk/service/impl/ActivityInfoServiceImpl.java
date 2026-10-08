package com.yzk.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.enums.ActivityType;
import com.yzk.mapper.ActivityInfoMapper;
import com.yzk.mapper.ActivityRuleMapper;
import com.yzk.mapper.ActivitySkuMapper;
import com.yzk.model.activity.ActivityInfo;
import com.yzk.model.activity.ActivityRule;
import com.yzk.model.activity.ActivitySku;
import com.yzk.model.product.SkuInfo;
import com.yzk.service.ActivityInfoService;
import com.yzk.vo.activity.ActivityRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            item.setActivityTypeString(ActivityType.getCommentByCode(item.getActivityType()));
        });
        return activityInfoList;
    }

    /**
     * 活动规则新增
     * @param activityRuleVo 活动规则参数
     */
    @Override
    public void saveActivityRule(ActivityRuleVo activityRuleVo) {
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
        return Collections.emptyMap();
    }

    /**
     * 模糊查询sku
     * @param keyword 查询key
     * @return
     */
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        return Collections.emptyList();
    }
}
