package com.yzk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.sys.Region;

import java.util.List;

/**
 * @description: 区域业务接口
 * @author: yzk
 * @date: 2026-10-05 11:55
 * @version: 1.0
 * @email: yzk@qq.com
 */
public interface RegionService extends IService<Region> {
    /**
     * 关键字查询区域
     * @param keyword
     * @return
     */
    List<Region> getRegionByKeyword(String keyword);
}