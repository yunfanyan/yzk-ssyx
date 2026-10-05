package com.yzk.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzk.model.product.SkuPoster;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商品海报表 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
@Mapper
@Repository
public interface SkuPosterMapper extends BaseMapper<SkuPoster> {

}
