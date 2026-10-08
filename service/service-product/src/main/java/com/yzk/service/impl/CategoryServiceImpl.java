package com.yzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.mapper.CategoryMapper;
import com.yzk.model.product.Category;
import com.yzk.service.CategoryService;
import com.yzk.vo.product.CategoryQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    /**
     * 条件分页查询
     * @param pageParam 分页参数
     * @param categoryQueryVo  条件参数
     * @return
     */
    @Override
    public IPage<Category> selectPageCategory(Page<Category> pageParam, CategoryQueryVo categoryQueryVo) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(categoryQueryVo.getName())){
            lambdaQueryWrapper.like(Category::getName, categoryQueryVo.getName());
        }

        return baseMapper.selectPage(pageParam,lambdaQueryWrapper);
    }
}
