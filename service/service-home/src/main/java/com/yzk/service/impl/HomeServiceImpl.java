package com.yzk.service.impl;


import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.yzk.client.ActivityFeignClient;
import com.yzk.client.SkuFeignClient;
import com.yzk.client.UserFeignClient;
import com.yzk.client.ProductFeignClient;
import com.yzk.model.product.Category;
import com.yzk.model.product.SkuInfo;
import com.yzk.model.search.SkuEs;
import com.yzk.service.HomeService;
import com.yzk.vo.user.LeaderAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private UserFeignClient  userFeignClient;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private SkuFeignClient skuFeignClient;
    @Autowired
    private ActivityFeignClient activityFeignClient;
    /**
     * 获取首页数据
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> homeData(Long userId) {
        userId=29l;
        Map<String,Object> result = new HashMap<>();
        //1 根据userId获取当前登录用户提货地址信息
        // 远程调用service-user模块接口获取需要数据
        LeaderAddressVo leaderAddressVo =
                userFeignClient.getUserAddressByUserId(userId);
        result.put("leaderAddressVo",leaderAddressVo);

        //2 获取所有分类
        // 远程调用service-product模块接口
        List<Category> categoryList = productFeignClient.findAllCategoryList();
        result.put("categoryList",categoryList);

        //3 获取新人专享商品
        // 远程调用service-product模块接口
        List<SkuInfo> newPersonSkuInfoList = productFeignClient.findNewPersonSkuInfoList();
        result.put("newPersonSkuInfoList",newPersonSkuInfoList);

        //4 获取爆款商品
        // 远程调用service-search模块接口
        // hotscore 热门评分降序排序
        //获取爆品商品
        List<SkuEs> hotSkuList = skuFeignClient.findHotSkuList();
        //获取sku对应的促销活动标签
        if(!CollectionUtils.isEmpty(hotSkuList)) {
            List<Long> skuIdList = hotSkuList.stream().map(sku -> sku.getId()).collect(Collectors.toList());
            Map<Long, List<String>> skuIdToRuleListMap = activityFeignClient.findActivity(skuIdList);
            if(null != skuIdToRuleListMap) {
                hotSkuList.forEach(skuEs -> {
                    skuEs.setRuleList(skuIdToRuleListMap.get(skuEs.getId()));
                });
            }
        }
        result.put("hotSkuList", hotSkuList);

        //5 封装获取数据到map集合，返回
        return result;
    }
}
