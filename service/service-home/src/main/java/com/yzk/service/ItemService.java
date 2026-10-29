package com.yzk.service;

import java.util.Map;

public interface ItemService {

    /**
     * 商品详情
     * @param id
     * @param userId
     * @return
     */
    Map<String, Object> item(Long id, Long userId);
}
