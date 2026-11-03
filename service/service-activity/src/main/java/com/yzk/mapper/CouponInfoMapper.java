package com.yzk.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzk.model.activity.CouponInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>
 * 优惠券信息 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2023-04-07
 */
@Mapper
@Repository
public interface CouponInfoMapper extends BaseMapper<CouponInfo> {


    //1 根据userId获取用户全部优惠卷
    List<CouponInfo> selectCartCouponInfoList(@Param("userId") Long userId);
}
