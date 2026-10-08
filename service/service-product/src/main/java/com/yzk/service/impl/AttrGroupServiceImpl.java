package com.yzk.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.mapper.AttrGroupMapper;
import com.yzk.model.product.AttrGroup;
import com.yzk.service.AttrGroupService;
import com.yzk.vo.product.AttrGroupQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 属性分组 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {
    @Override
    public IPage<AttrGroup> selectPageAttrGroup(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo) {
        LambdaQueryWrapper<AttrGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(attrGroupQueryVo.getName())){
            lambdaQueryWrapper.like(AttrGroup::getName, attrGroupQueryVo.getName());
        }
        return  baseMapper.selectPage(pageParam,lambdaQueryWrapper);

    }

    @Override
    public List<AttrGroup> findAllListAttrGroup() {
        LambdaQueryWrapper<AttrGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(AttrGroup::getId);
        return baseMapper.selectList(lambdaQueryWrapper);
    }
}
