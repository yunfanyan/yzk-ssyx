package com.yzk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.acl.Permission;

import java.util.List;
import java.util.Map;

/**
 * @description: 权限业务接口
 * @author: yzk
 * @date: 2026-10-05 00:00
 * @version: 1.0
 * @email: yzk@qq.com
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 查询所有菜单信息
     * @return
     */
    List<Permission> queryAllPermission();

    /**
     * 递归删除
     * @param id
     */
    void removeChildById(Long id);

    /**
     * 给角色分配权限
     * @param roleId 角色ID
     * @param permissionIds 权限ID
     */
    void doAssign(Long roleId, Long[] permissionIds);

    /**
     * 根据ID查询角色权限
     * @param roleId 角色ID
     * @return
     */
   List<Permission> toAssign(Long roleId);
}