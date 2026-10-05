package com.yzk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.acl.Admin;
import com.yzk.model.acl.Role;
import com.yzk.vo.acl.RoleQueryVo;

import java.util.Map;

/**
 * @description: 角色业务接口
 * @author: yzk
 * @date: 2026-10-04 21:29
 * @version: 1.0
 * @email: yzk@qq.com
 */
public interface RoleService extends IService<Role> {
    /**
     * 条件查询角色信息
     * @param pagePara 分页条件
     * @param roleQueryVo 查询信息
     * @return
     */
    IPage<Role> getPageAdmins(Page<Role> pagePara, RoleQueryVo roleQueryVo);

    /**
     * 为用户分配角色
     * @param adminId 用户ID
     * @param roleId 角色ID
     */
    void saveAdminRole(Long adminId, Long[] roleId);

    /**
     * 获取用户角色信息
     * @param adminId 用户ID
     * @return
     */
    Map<String, Object> getRoleByAdminId(Long adminId);
}