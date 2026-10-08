package com.yzk.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.constant.MqConst;
import com.yzk.mapper.SkuInfoMapper;
import com.yzk.model.product.SkuAttrValue;
import com.yzk.model.product.SkuImage;
import com.yzk.model.product.SkuInfo;
import com.yzk.model.product.SkuPoster;
import com.yzk.service.*;
import com.yzk.vo.product.SkuInfoQueryVo;
import com.yzk.vo.product.SkuInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {
    @Autowired
    private SkuPosterService skuPosterService;
    @Autowired
    private SkuImageService skuImageService;
    @Autowired
    private SkuAttrValueService skuAttrValueService;
    @Autowired
    private RabbitService rabbitService;
    /**
     * 保存sku
     * @param skuInfoVo 信息条件
     */
    @Override
    public void saveSkuInfo(SkuInfoVo skuInfoVo) {
        //1.保存sku信息
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.insert(skuInfo);
        //2.保存sku海报
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        for(SkuPoster skuPoster:skuPosterList){
            skuPoster.setId(skuInfo.getId());
        }
        skuPosterService.saveBatch(skuPosterList);
        //3.保存sku图片
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        for(SkuImage skuImage:skuImagesList){
            skuImage.setId(skuInfo.getId());
        }
        skuImageService.saveBatch(skuImagesList);
        //4.保存sku属性值
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        for(SkuAttrValue skuAttrValue:skuAttrValueList){
            skuAttrValue.setId(skuInfo.getId());
        }
        skuAttrValueService.saveBatch(skuAttrValueList);



    }

    /**
     * 修改sku
     * @param skuInfoVo 修改条件
     */
    @Override
    public void updateSkuInfo(SkuInfoVo skuInfoVo) {
        //1.修改sku信息
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.updateById(skuInfo);
        //2.修改sku海报
        if(!CollectionUtils.isEmpty(skuInfoVo.getSkuPosterList())) {
            List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setId(skuInfo.getId());
            }
            skuPosterService.updateBatchById(skuPosterList);
        }
        //3.修改sku图片
        if(!CollectionUtils.isEmpty(skuInfoVo.getSkuImagesList())) {
            List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
            for (SkuImage skuImage : skuImagesList) {
                skuImage.setId(skuInfo.getId());
            }
            skuImageService.updateBatchById(skuImagesList);
        }
        //4.修改sku属性值
        if(!CollectionUtils.isEmpty(skuInfoVo.getSkuAttrValueList())) {
            List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setId(skuInfo.getId());
            }
            skuAttrValueService.updateBatchById(skuAttrValueList);
        }

    }

    /**
     * 根据id查询sku
     * @param id
     * @return
     */
    @Override
    public SkuInfoVo getSkuInfo(Long id) {
        //1.查询skuInfo
        SkuInfo skuInfo = baseMapper.selectById(id);
        //2.查询sku海报
        LambdaQueryWrapper<SkuPoster> posterQueryWrapper = new LambdaQueryWrapper();
        posterQueryWrapper.eq(SkuPoster::getSkuId,id);
        List<SkuPoster> skuPosterList = skuPosterService.list(posterQueryWrapper);
        //3.查询sku图片
        LambdaQueryWrapper<SkuImage> imageWrapper = new LambdaQueryWrapper();
        imageWrapper.eq(SkuImage::getSkuId,id);
        List<SkuImage> skuImageList = skuImageService.list(imageWrapper);
        //4.查询sku属性值
        LambdaQueryWrapper<SkuAttrValue> attrValueWrapper = new LambdaQueryWrapper();
        attrValueWrapper.eq(SkuAttrValue::getSkuId,id);
        List<SkuAttrValue> skuAttrValueList = skuAttrValueService.list(attrValueWrapper);

        SkuInfoVo skuInfoVo = new SkuInfoVo();
        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuPosterList(skuPosterList);
        skuInfoVo.setSkuImagesList(skuImageList);
        skuInfoVo.setSkuAttrValueList(skuAttrValueList);
        return skuInfoVo;
    }

    /**
     * 分页条件查询
     * @param pageParam 分页条件
     * @param skuInfoQueryVo 查询条件
     * @return
     */
    @Override
    public IPage<SkuInfo> selectPageSkuInfo(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo) {
        LambdaQueryWrapper<SkuInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(skuInfoQueryVo.getCategoryId())){
            lambdaQueryWrapper.eq(SkuInfo::getCategoryId,skuInfoQueryVo.getCategoryId());
        }
        if(!StringUtils.isEmpty(skuInfoQueryVo.getSkuType())){
            lambdaQueryWrapper.eq(SkuInfo::getSkuType,skuInfoQueryVo.getSkuType());
        }
        if(!StringUtils.isEmpty(skuInfoQueryVo.getKeyword())){
            lambdaQueryWrapper.like(SkuInfo::getSkuName,skuInfoQueryVo.getKeyword());
        }

        return baseMapper.selectPage(pageParam,lambdaQueryWrapper);
    }

    /**
     * 商品审核
     * @param skuId
     * @param status sku状态
     */
    @Override
    public void check(Long skuId, Integer status) {
        SkuInfo skuInfo = baseMapper.selectById(skuId);
        skuInfo.setCheckStatus(status);
        baseMapper.updateById(skuInfo);
    }

    /**
     * 商品上下架
     * @param skuId
     * @param status
     */
    @Override
    public void publish(Long skuId, Integer status) {
        if(status == 1) { //上架
            SkuInfo skuInfo = baseMapper.selectById(skuId);
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);
            // 整合mq把数据同步到es里面
            rabbitService.sendMegger(MqConst.EXCHANGE_GOODS_DIRECT,MqConst.ROUTING_GOODS_UPPER,skuId);

        } else { //下架
            SkuInfo skuInfo = baseMapper.selectById(skuId);
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);
            // 整合mq把数据同步到es里面
            rabbitService.sendMegger(MqConst.EXCHANGE_GOODS_DIRECT,MqConst.ROUTING_GOODS_LOWER,skuId);

        }
    }

    /**
     * 新人专享
     * @param skuId
     * @param status
     */
    @Override
    public void isNewPerson(Long skuId, Integer status) {
        SkuInfo skuInfoUp = new SkuInfo();
        skuInfoUp.setId(skuId);
        skuInfoUp.setIsNewPerson(status);
        baseMapper.updateById(skuInfoUp);
    }

    /**
     * 批量获取sku信息
     * @param skuIdList
     * @return
     */
    @Override
    public List<SkuInfo> findSkuInfoList(List<Long> skuIdList) {
        return baseMapper.selectBatchIds(skuIdList);
    }

    /**
     * 关键字获取sku 信息
     * @param keyword
     * @retur
     */
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        return baseMapper.selectList(new LambdaQueryWrapper<SkuInfo>().like(SkuInfo::getSkuName,keyword));
    }
}
