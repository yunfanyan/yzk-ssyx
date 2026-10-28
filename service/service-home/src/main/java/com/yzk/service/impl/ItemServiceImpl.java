<<<<<<< HEAD
package com.yzk.service.impl;



import com.yzk.client.ActivityFeignClient;
import com.yzk.client.ProductFeignClient;
import com.yzk.client.SkuFeignClient;
import com.yzk.model.product.SkuInfo;
import com.yzk.service.ItemService;
import com.yzk.vo.product.SkuInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ItemServiceImpl implements ItemService {
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private ActivityFeignClient activityFeignClient;
    @Autowired
    private SkuFeignClient skuFeignClient;


    /**
     * 获取商品详情
     * @param skuId
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> item(Long skuId, Long userId) {
        HashMap<String, Object> result = new HashMap<>();

        //1.获取商品sukInfo信息
        CompletableFuture<SkuInfoVo> skuInfoVoCompletableFuture = CompletableFuture.supplyAsync(() -> {
            SkuInfoVo skuInfoVo = productFeignClient.findSkuInfoBySku(skuId);
            result.put("skuInfoVo", skuInfoVo);
            return skuInfoVo;
        }, threadPoolExecutor);

        //获取活动规则数据和优惠劵信息
        CompletableFuture<Map<String, Object>> mapCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Map<String, Object> activityMap =
                    activityFeignClient.findActivityAndCoupon(skuId, userId);
            result.putAll(activityMap);
            return activityMap;
        }, threadPoolExecutor);


        //添加商品热度
        CompletableFuture<Void> hotCompletableFuture = CompletableFuture.runAsync(() -> {
            //远程调用更新热度
            skuFeignClient.incrHotScore(skuId);
        }, threadPoolExecutor);
        CompletableFuture.allOf(
                skuInfoVoCompletableFuture,
                mapCompletableFuture,
                hotCompletableFuture
        ).join();
        return result;
    }
}
=======
>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)
