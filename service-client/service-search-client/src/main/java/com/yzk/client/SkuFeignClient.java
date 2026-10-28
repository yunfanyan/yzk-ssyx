<<<<<<< HEAD
package com.yzk.client;

import com.yzk.model.search.SkuEs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @description: 搜索模块feign 消费端
 * @date: 2026-10-11 10:07
 * @version: 1.0
 * @email: yzk@qq.com
 */
@FeignClient("service-search")
public interface SkuFeignClient {
    /**
     * 获取热销商品列表
     * @return
     */
    @GetMapping("/api/search/sku/inner/findHotSkuList")
    public List<SkuEs> findHotSkuList();

    //更新商品热度
    @GetMapping("/api/search/sku/inner/incrHotScore/{skuId}")
    public Boolean incrHotScore(@PathVariable("skuId") Long skuId);

}
=======
>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)
