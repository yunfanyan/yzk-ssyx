package com.yzk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.sys.RegionWare;
import com.yzk.vo.sys.RegionWareQueryVo;

/**
 * @description: 区域仓库业务接口
 * @author: yzk
 * @date: 2026-10-05 23:22
 * @version: 1.0
 * @email: yzk@qq.com
 */
public interface RegionWareService extends IService<RegionWare> {

    /**
     * 开通区域列表
     * @param pageParam
     * @param regionWareQueryVo
     * @return
     */
    IPage<RegionWare> selectPageRegionWare(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo);

    /**
     * 新增开通
     * @param regionWare
     */
    void saveRegionWare(RegionWare regionWare);

    /**
     * 取消开通
     * @param id
     * @param status
     */
    void updateStatus(Long id, Integer status);
}