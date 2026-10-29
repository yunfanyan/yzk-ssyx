package com.yzk.service.impl;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.client.ProductFeignClient;
import com.yzk.enums.SkuType;
import com.yzk.mapper.SeckillMapper;
import com.yzk.mapper.SeckillSkuMapper;
import com.yzk.model.activity.Seckill;
import com.yzk.model.activity.SeckillSku;
import com.yzk.model.product.SkuInfo;
import com.yzk.service.SeckillService;
import com.yzk.service.SeckillSkuService;
import com.yzk.vo.activity.SeckillSkuQueryVo;
import com.yzk.vo.activity.SeckillSkuVo;
import com.yzk.vo.product.SkuStockLockVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * @description: 类功能描述
 * @date: 2026-10-09 02:05
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Service
public class SeckillSkuServiceImpl extends ServiceImpl<SeckillSkuMapper, SeckillSku> implements SeckillSkuService {
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private SeckillSkuMapper seckillSkuMapper;
    /**
     * 条件分页查询
     * @param pageParam 分页对象
     * @param seckillSkuQueryVo 条件对象
     * @return
     */
    @Override
    public IPage<SeckillSku> selectPage(Page<SeckillSku> pageParam, SeckillSkuQueryVo seckillSkuQueryVo) {
        //获取秒杀sku信息集合
        IPage<SeckillSku> seckillSkuIPage = seckillSkuMapper.selectSeckillSku(pageParam, seckillSkuQueryVo);
        if(CollectionUtils.isEmpty(seckillSkuIPage.getRecords())){
            return seckillSkuIPage;
        }
        //获取对应的sku信息
        List<Long> skuIds = seckillSkuIPage.getRecords().stream().map(SeckillSku::getSkuId).collect(Collectors.toList());
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(skuIds);
        Map<Long, SkuInfo> skuInfoMap = skuInfoList.stream().collect(Collectors.toMap(SkuInfo::getId, SkuInfo -> SkuInfo));

        seckillSkuIPage.getRecords().stream().forEach(item->item.setSkuInfo(skuInfoMap.get(item.getSkuId())));

        return seckillSkuIPage;

    }

    @Override
    public void save(List<SeckillSku> seckillSkuList) {
        this.saveBatch(seckillSkuList);

        //更新sku为秒杀商品
        List<Long> skuIdList = seckillSkuList.stream().map(SeckillSku::getSkuId).collect(Collectors.toList());
        productFeignClient.updateSkuType(skuIdList, SkuType.SECKILL);
    }

    @Override
    public void saveSeckillSkuListToCache(String date) {

    }

    @Override
    public List<SeckillSkuVo> findSeckillSkuListFromCache(String timeName) {
        return Collections.emptyList();
    }

    @Override
    public SeckillSkuVo getSeckillSkuVo(Long skuId) {
        return null;
    }

    @Override
    public Boolean checkAndMinusStock(List<SkuStockLockVo> skuStockLockVoList, String orderToken) {
        return null;
    }

    @Override
    public void rollBackStock(String orderNo) {

    }
}