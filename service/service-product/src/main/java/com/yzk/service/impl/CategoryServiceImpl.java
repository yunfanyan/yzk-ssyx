package com.yzk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.mapper.CategoryMapper;
import com.yzk.model.product.Category;
import com.yzk.service.CategoryService;
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


}
