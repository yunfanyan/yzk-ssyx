package com.yzk.service.impl;



import com.yzk.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {
    /**
     * 获取商品详情
     * @param id
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> item(Long id, Long userId) {
        return Collections.emptyMap();
    }
}
