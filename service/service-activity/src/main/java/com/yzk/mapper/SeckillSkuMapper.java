package com.yzk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzk.model.activity.SeckillSku;
import com.yzk.vo.activity.SeckillSkuQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @description: 接口功能描述
 * @author: yzk
 * @date: 2026-10-09 01:58
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Mapper
@Repository
public interface SeckillSkuMapper extends BaseMapper<SeckillSku> {

    IPage<SeckillSku> selectSeckillSku(Page<SeckillSku> pageParam, @Param("vo") SeckillSkuQueryVo seckillSkuQueryVo);
}