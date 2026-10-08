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
import com.yzk.model.product.SkuInfo;
import com.yzk.service.ActivityInfoService;
import com.yzk.vo.activity.ActivityRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
}
