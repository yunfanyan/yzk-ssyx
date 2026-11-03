package com.yzk.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzk.model.activity.ActivityInfo;
import com.yzk.model.activity.ActivityRule;
import com.yzk.model.activity.ActivitySku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 * 活动表 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2023-04-07
 */
@Mapper
@Repository
public interface ActivityInfoMapper extends BaseMapper<ActivityInfo> {

    /**
     * 查询正在活动中的skuId
     * @param skuIds
     * @return
     */
    List<Long> findActivityIdIsExist(@Param("skuIds")List<Long> skuIds);

    /**
     * 根据ID获取商品规则列表
     * @param skuId
     * @return
     */
    List<ActivityRule> findActivityRule(@Param("skuId") Long skuId);

    /**
     * 获取参加活动的商品信息
     * @param skuIdList
     * @return
     */
    //根据所有skuId列表获取参与活动
    List<ActivitySku> selectCartActivity(@Param("skuIdList") List<Long> skuIdList);
}
