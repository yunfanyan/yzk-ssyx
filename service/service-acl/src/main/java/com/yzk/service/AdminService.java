package com.yzk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.acl.Admin;
import com.yzk.vo.acl.AdminQueryVo;

import java.util.List;

/**
 * @description: 管理员业务类
 * @author: yzk
 * @date: 2026-10-04 19:22
 * @version: 1.0
 * @email: yzk@qq.com
 */
public interface AdminService extends IService<Admin> {
    /**
     * 分页查询条件查询管理员信息
     * @param pagePara 分页条件
     * @param adminQueryVo 查询条件
     * @return
     */
    IPage<Admin> getPageAdmins(Page<Admin> pagePara, AdminQueryVo adminQueryVo);
}