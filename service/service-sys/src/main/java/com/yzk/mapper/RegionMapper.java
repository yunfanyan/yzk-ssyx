package com.yzk.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzk.model.sys.Region;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description: 区域数据访问类
 * @date: 2026-10-05 11:51
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Mapper
@Repository
public interface RegionMapper extends BaseMapper<Region> {
}