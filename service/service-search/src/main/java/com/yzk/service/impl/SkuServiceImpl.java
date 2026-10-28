package com.yzk.service.impl;


import com.yzk.client.ProductFeignClient;
import com.yzk.common.exception.SsyxException;
import com.yzk.enums.SkuType;
import com.yzk.model.product.Category;
import com.yzk.model.product.SkuInfo;
import com.yzk.model.search.SkuEs;
import com.yzk.repository.SkuRepository;
import com.yzk.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
=======
import org.springframework.stereotype.Service;
>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)

/**
 * @description: es-sku业务实现类
 * @date: 2026-10-08 00:31
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private SkuRepository skuRepository;
<<<<<<< HEAD
    @Autowired
    private ActivityFeignClient activityFeignClient;
    @Autowired
    private RedisTemplate redisTemplate;
=======
>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)
    /**
     * 上架
     * @param skuId
     */
    @Override
    public void upperSku(Long skuId) {
        //1.查询sku信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if(skuInfo==null){
            throw new SsyxException("查询失败!", 201);
        }
        //2.查询sku分组信息
        Category category = productFeignClient.getCategory(skuInfo.getCategoryId());
        //2 获取数据封装SkuEs对象
        SkuEs skuEs = new SkuEs();
        //封装分类
        if(category != null) {
            skuEs.setCategoryId(category.getId());
            skuEs.setCategoryName(category.getName());
        }
        //封装sku信息部分
        skuEs.setId(skuInfo.getId());
        skuEs.setKeyword(skuInfo.getSkuName()+","+skuEs.getCategoryName());
        skuEs.setWareId(skuInfo.getWareId());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());
        if(skuInfo.getSkuType() == SkuType.COMMON.getCode()) {//普通商品
            skuEs.setSkuType(0);
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        }else {
            //TODO 待完善-秒杀商品

        }
        //es中新增
        skuRepository.save(skuEs);

    }

    /**
     * 下架
     * @param skuId
     */
    @Override
    public void lowerSku(Long skuId) {
        //es中删除
        skuRepository.deleteById(skuId);
    }
<<<<<<< HEAD

    /**
     * 搜索热门商品
     * @return
     */
    @Override
    public List<SkuEs> findHotSkuList() {
        Pageable pageable = PageRequest.of(0,10);
        Page<SkuEs> pageModel = skuRepository.findByOrderByHotScoreDesc(pageable);
        List<SkuEs> content = pageModel.getContent();
        return content;
    }

    /**
     * 更新商品热度
     * @param skuId
     * @return
     */
    @Override
    public Boolean incrHotScore(Long skuId) {
        String key = "hotScore";
        Double keyScore = redisTemplate.opsForZSet().incrementScore(key, skuId, 1);
        if(keyScore%10==0){
            Optional<SkuEs> byId = skuRepository.findById(skuId);
            SkuEs skuEs = byId.get();
            skuEs.setHotScore(keyScore.longValue());
            skuRepository.save(skuEs);
        }
        return true;
    }

    /**
     * 条件分页查询商品列表
     * @param pageable 分页条件
     * @param searchParamVo 查询条件
     * @return
     */
    @Override
    public Page<SkuEs> search(Pageable pageable, SkuEsQueryVo searchParamVo) {
        //1 向SkuEsQueryVo设置wareId，当前登录用户的仓库id
        searchParamVo.setWareId(AuthContextHolder.getWareId());

        Page<SkuEs> pageModel = null;
        //2 调用SkuRepository方法，根据springData命名规则定义方法，进行条件查询
        //// 判断keyword是否为空，如果为空 ，根据仓库id + 分类id查询
        String keyword =  searchParamVo.getKeyword();
        if(StringUtils.isEmpty(keyword)) {
            pageModel =
                    skuRepository
                            .findByCategoryIdAndWareId(
                                    searchParamVo.getCategoryId(),
                                    searchParamVo.getWareId(),
                                    pageable);
        } else {
            ///如果keyword不为空根据仓库id + keyword进行查询
            pageModel = skuRepository
                    .findByKeywordAndWareId(
                             searchParamVo.getKeyword(),
                            searchParamVo.getWareId(),
                            pageable);
        }

        //3 查询商品参加优惠活动
        List<SkuEs> skuEsList = pageModel.getContent();

        if(!CollectionUtils.isEmpty(skuEsList)) {
            //遍历skuEsList，得到所有skuId
            List<Long> skuIdList =
                    skuEsList.stream()
                            .map(item -> item.getId())
                            .collect(Collectors.toList());
            //根据skuId列表远程调用，调用service-activity里面的接口得到数据
            //返回Map<Long,List<String>>
            //// map集合key就是skuId值，Long类型
            //// map集合value是List集合，sku参与活动里面多个规则名称列表
            ///// 一个商品参加一个活动，一个活动里面可以有多个规则
            ////// 比如有活动：中秋节满减活动
            ////// 一个活动可以有多个规则：
            ////// 中秋节满减活动有两个规则：满20元减1元，满58元减5元
            Map<Long,List<String>> skuIdToRuleListMap =
                    activityFeignClient.findActivity(skuIdList);//远程调用
            //封装获取数据到skuEs里面 ruleList属性里面
            if(skuIdToRuleListMap != null) {
                skuEsList.forEach(skuEs -> {
                    skuEs.setRuleList(skuIdToRuleListMap.get(skuEs.getId()));
                });
            }
        }
        return pageModel;
    }
=======
>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)
}