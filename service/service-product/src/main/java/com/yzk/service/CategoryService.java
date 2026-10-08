package com.yzk.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.product.Category;
import com.yzk.vo.product.CategoryQueryVo;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
public interface CategoryService extends IService<Category> {

    /**
     * 条件分页查询
     * @param pageParam 分页参数
     * @param categoryQueryVo  条件参数
     * @return
     */
    IPage<Category> selectPageCategory(Page<Category> pageParam, CategoryQueryVo categoryQueryVo);
}
