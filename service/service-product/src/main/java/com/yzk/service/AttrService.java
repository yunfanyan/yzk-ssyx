package com.yzk.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.product.Attr;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
public interface AttrService extends IService<Attr> {

    /**
     * 根据分组id查询商品属性列表
     * @param groupId
     * @return
     */
    List<Attr> getAttrListByGroupId(Long groupId);
}
