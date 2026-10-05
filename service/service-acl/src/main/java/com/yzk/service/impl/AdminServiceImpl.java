package com.yzk.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.mapper.AdminMapper;
import com.yzk.model.acl.Admin;
import com.yzk.service.AdminService;
import com.yzk.vo.acl.AdminQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @description: 管理员业务实现类
 * @date: 2026-10-04 19:22
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    /**
     * 分页查询管理员信息
     * @param pagePara 分页条件
     * @param adminQueryVo 查询条件
     * @return
     */
    @Override
    public IPage<Admin> getPageAdmins(Page<Admin> pagePara, AdminQueryVo adminQueryVo) {
        IPage<Admin> admins = adminMapper.pageList(pagePara,adminQueryVo);
        return admins;
    }
}