package com.yzk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.mapper.WareMapper;
import com.yzk.model.sys.Ware;
import com.yzk.service.WareService;
import org.springframework.stereotype.Service;

/**
 * @description: 仓库业务实现类
 * @date: 2026-10-05 22:26
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Service
public class WareServiceImpl extends ServiceImpl<WareMapper, Ware> implements WareService {
}