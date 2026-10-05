package com.yzk.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzk.model.product.SkuImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商品图片 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
@Mapper
@Repository
public interface SkuImageMapper extends BaseMapper<SkuImage> {

}
