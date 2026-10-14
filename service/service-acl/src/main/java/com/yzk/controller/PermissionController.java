package com.yzk.controller;

import com.yzk.common.result.Result;
import com.yzk.model.acl.Permission;
import com.yzk.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 权限控制器
 * @date: 2026-10-05 00:06
 * @version: 1.0
 * @email: yzk@qq.com
 */
@RestController
@RequestMapping("/admin/acl/permission")
@Api(tags = "菜单管理")
//@CrossOrigin //跨域
public class PermissionController {
    @Autowired
    private PermissionService permissionService;




    @ApiOperation("添加菜单")
    @PostMapping("save")
    public Result save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok(null);
    }

    @ApiOperation("递归删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        permissionService.removeChildById(id);
        return Result.ok(null);
    }


    @ApiOperation("修改菜单")
    @PutMapping("update")
    public Result update(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok(null);
    }
    @ApiOperation("查询所有菜单")
    @GetMapping
    public Result list() {
        List<Permission> list = permissionService.queryAllPermission();
        return Result.ok(list);
    }
    @ApiOperation("为角色进行权限分配")
    @PostMapping("doAssign")
    public Result doAssign(@RequestParam Long roleId,
                           @RequestParam Long[] permissionId) {
        permissionService.doAssign(roleId,permissionId);
        return Result.ok(null);
    }

    @ApiOperation("获取角色权限")
    @GetMapping("toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId) {
        //返回map集合包含两部分数据：所有角色 和 为用户分配角色列表
        List<Permission> permissions  = permissionService.toAssign(roleId);
        return Result.ok(permissions);
    }
}