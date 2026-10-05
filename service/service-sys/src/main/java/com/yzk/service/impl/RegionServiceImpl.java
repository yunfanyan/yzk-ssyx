package com.yzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.mapper.RegionMapper;
import com.yzk.model.sys.Region;
import com.yzk.service.RegionService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @description: 区域业务实现类
 * @date: 2026-10-05 11:56
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {
    /**
     * 关键字查询区域
     * @param keyword
     * @return
     */
    @Override
    public List<Region> getRegionByKeyword(String keyword) {
        LambdaQueryWrapper<Region> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Region::getName,keyword);

        return baseMapper.selectList(wrapper);
    }
}