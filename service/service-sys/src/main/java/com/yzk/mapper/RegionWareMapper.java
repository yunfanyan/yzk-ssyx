package com.yzk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzk.model.sys.RegionWare;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description: 区域仓库数据访问类
 * @author: yzk
 * @date: 2026-10-05 23:20
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Mapper
@Repository
public interface RegionWareMapper extends BaseMapper<RegionWare> {

}