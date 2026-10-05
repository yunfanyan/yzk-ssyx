package com.yzk.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzk.model.product.SkuInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * sku信息 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
@Mapper
@Repository
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {

}
