package com.yzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.mapper.AdminRoleMapper;
import com.yzk.mapper.RoleMapper;
import com.yzk.model.acl.AdminRole;
import com.yzk.model.acl.Role;
import com.yzk.service.RoleService;
import com.yzk.vo.acl.RoleQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @description: 角色业务实现接口
 * @date: 2026-10-04 21:30
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    /**
     * 条件查询角色信息
     * @param pagePara 分页条件
     * @param roleQueryVo 查询信息
     * @return
     */
    @Override
    public IPage<Role> getPageAdmins(Page<Role> pagePara, RoleQueryVo roleQueryVo) {
        if(!StringUtils.isEmpty(roleQueryVo.getRoleName())){
            LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(Role::getRoleName,roleQueryVo.getRoleName());
            return roleMapper.selectPage(pagePara, lambdaQueryWrapper);
        }
        return roleMapper.selectPage(pagePara, null);
    }

    /**
     * 给用户分配角色
     * @param adminId 用户ID
     * @param roleId 角色ID
     */
    @Override
    public void saveAdminRole(Long adminId, Long[] roleId) {
        //1.删除之前的记录
        LambdaQueryWrapper<AdminRole> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(AdminRole::getAdminId,adminId);
        adminRoleMapper.delete(lambdaQueryWrapper);

        //2.增加现在的记录
        for(Long id:roleId){
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(id);
            adminRoleMapper.insert(adminRole);
        }



    }

    /**
     * 查询用户角色信息
     * @param adminId 用户ID
     * @return
     */
    @Override
    public Map<String, Object> getRoleByAdminId(Long adminId) {
        //1.查询所有的角色列表
        List<Role> allRolesList = baseMapper.selectList(null);
        //2.查询用户配分配的角色列表
        LambdaQueryWrapper<AdminRole> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(AdminRole::getAdminId,adminId);
        List<AdminRole> adminRoles = adminRoleMapper.selectList(lambdaQueryWrapper);
        //获取分配角色的ID
        List<Long> ids = adminRoles.stream()
                .map(a->a.getRoleId())
                .collect(Collectors.toList());
        ArrayList<Role> roleList = new ArrayList<>();
        for(Role role:allRolesList){
            if(ids.contains(role.getId())){
                roleList.add(role);
            }
        }
        //封装到map，返回
        Map<String, Object> result = new HashMap<>();
        //所有角色列表
        result.put("allRolesList",allRolesList);
        //用户分配角色列表
        result.put("assignRoles",roleList);
        return result;
    }
}