package com.yzk.service;

import java.util.Map;

public interface HomeService {

    /**
     * 获取首页数据
     * @param userId
     * @return
     */
    Map<String, Object> homeData(Long userId);
}
