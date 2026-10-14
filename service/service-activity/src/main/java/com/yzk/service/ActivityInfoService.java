package com.yzk.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.activity.ActivityInfo;
import com.yzk.model.product.SkuInfo;
import com.yzk.vo.activity.ActivityRuleVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-07
 */
public interface ActivityInfoService extends IService<ActivityInfo> {
    /**
     * 删除活动
     * @param id
     */
    void removeActivity(Long id);
    /**
     * 分页查询
     * @param pageParam 分页条件
     * @return
     */
    IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam);

    /**
     * 活动规则新增
     * @param activityRuleVo 活动规则参数
     */
    void saveActivityRule(ActivityRuleVo activityRuleVo);

    /**
     * 查询活动规则列表
     * @param id 活动ID
     * @return
     */
    Map<String, Object> findActivityRuleList(Long id);

    /**
     * sku模块查询
     * @param keyword 查询key
     * @return
     */
    List<SkuInfo> findSkuInfoByKeyword(String keyword);

    /**
     * 获取商品促销活动信息
     * @param skuIdList
     * @return
     */
    Map<Long, List<String>> findActivity(List<Long> skuIdList);
}
