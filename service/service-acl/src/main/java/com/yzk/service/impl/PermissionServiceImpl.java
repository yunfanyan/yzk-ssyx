package com.yzk.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.mapper.PermissionMapper;
import com.yzk.mapper.RolePermissionMapper;
import com.yzk.model.acl.Permission;
import com.yzk.model.acl.RolePermission;
import com.yzk.service.PermissionService;
import com.yzk.utils.PermissionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @description: 权限业务实现类
 * @date: 2026-10-05 00:01
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper,Permission> implements PermissionService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    /**
     * 查询所有菜单信息
     * @return
     */
    @Override
    public List<Permission> queryAllPermission() {
        List<Permission> permissions = baseMapper.selectList(null);
         return PermissionHelper.buildPermission(permissions);

    }

    @Override
    public void removeChildById(Long id) {
        List<Long> idList = new ArrayList<>();

        //递归查询ID
        this.getAllPermissionId(id,idList);

        idList.add(id);

        baseMapper.deleteBatchIds(idList);

    }

    /**
     * 递归查询用户菜单ID
     * @param id
     * @param idList
     */
    private void getAllPermissionId(Long id, List<Long> idList) {
        LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Permission::getPid,id);
        List<Permission> permissions = baseMapper.selectList(lambdaQueryWrapper);

        permissions.stream().forEach(item->{
            idList.add(item.getId());
            this.getAllPermissionId(item.getId(), idList);
        });
    }

    /**
     * 给角色分配权限
     * @param roleId 角色ID
     * @param permissionIds 权限ID
     */
    @Override
    public void doAssign(Long roleId, Long[] permissionIds) {
        //1.删除之前的记录
        LambdaQueryWrapper<RolePermission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RolePermission::getRoleId,roleId);
        rolePermissionMapper.delete(lambdaQueryWrapper);

        //2.新增修改的记录
        for(Long permissionId:permissionIds){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionMapper.insert(rolePermission);
        }

    }

    /**
     * 查询角色权限
     * @param roleId 角色ID
     * @return
     */
    @Override
    public List<Permission> toAssign(Long roleId) {
        //1.查询所有的权限
        LambdaQueryWrapper<Permission> lambdaWrapper= new LambdaQueryWrapper<>();
        lambdaWrapper.eq(Permission::getStatus,"1");
        List<Permission> permissions = baseMapper.selectList(lambdaWrapper);
        //2.查询角色分配的权限id
        LambdaQueryWrapper<RolePermission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RolePermission::getRoleId,roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(lambdaQueryWrapper);
        List<Long> permissionIds = rolePermissions.stream().map(r -> r.getPermissionId()).collect(Collectors.toList());

        //3.设置权限的选中值
        permissions.stream().forEach(p->{
            if(permissionIds.contains(p.getId())){
                p.setSelect(true);
            }else{
                p.setSelect(false);
            }
        });
       return  PermissionHelper.buildPermission(permissions);

    }
}