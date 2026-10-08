package com.yzk.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.product.AttrGroup;
import com.yzk.vo.product.AttrGroupQueryVo;

import java.util.List;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
public interface AttrGroupService extends IService<AttrGroup> {

    /**
     * 条件分页查询
     * @param pageParam 分页条件
     * @param attrGroupQueryVo 查询条件
     * @return
     */
    IPage<AttrGroup> selectPageAttrGroup(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo);

    /**
     * 查询所有分组
     * @return
     */
    List<AttrGroup> findAllListAttrGroup();
}
