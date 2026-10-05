package com.yzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.common.exception.SsyxException;
import com.yzk.common.result.ResultCodeEnum;
import com.yzk.mapper.RegionMapper;
import com.yzk.mapper.RegionWareMapper;
import com.yzk.model.sys.RegionWare;
import com.yzk.service.RegionService;
import com.yzk.service.RegionWareService;
import com.yzk.vo.sys.RegionWareQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @description: 类功能描述
 * @date: 2026-10-05 23:23
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Service
public class RegionWareServiceImpl extends ServiceImpl<RegionWareMapper, RegionWare> implements RegionWareService {
    /**
     * 添加开通区域
     * @param regionWare
     */
    @Override
    public void saveRegionWare(RegionWare regionWare) {
        //1.判断区域是否开通
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RegionWare::getRegionId,regionWare.getRegionId());
        Integer i = baseMapper.selectCount(wrapper);
        if(i>0){
            throw new SsyxException(ResultCodeEnum.REGION_OPEN);
        }
        baseMapper.insert(regionWare);
    }

    /**
     * 取消开通
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, Integer status) {
        RegionWare regionWare = new RegionWare();
        regionWare.setId(id);
        regionWare.setStatus(status);
        baseMapper.updateById(regionWare);
    }

    /**
     * 开通区域列表
     * @param pageParam
     * @param regionWareQueryVo
     * @return
     */
    @Override
    public IPage<RegionWare> selectPageRegionWare(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo) {
        LambdaQueryWrapper<RegionWare> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(regionWareQueryVo.getKeyword())){
            lambdaQueryWrapper.like(RegionWare::getRegionName,regionWareQueryVo.getKeyword())
                    .or()
                    .like(RegionWare::getWareName,regionWareQueryVo.getKeyword());
        }
        IPage<RegionWare> regionWarePage = baseMapper.selectPage(pageParam,lambdaQueryWrapper);
        return regionWarePage;
    }
}