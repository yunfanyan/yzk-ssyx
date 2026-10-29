package com.yzk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzk.common.result.Result;
import com.yzk.model.acl.Admin;

import com.yzk.model.acl.Permission;
import com.yzk.model.acl.Role;
import com.yzk.service.PermissionService;
import com.yzk.service.RoleService;
import com.yzk.vo.acl.RoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description: 角色控制器
 * @date: 2026-10-04 21:32
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Api(tags="角色管理")
@RestController()
@RequestMapping("/admin/acl/role")
//@CrossOrigin
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;


    @ApiOperation("角色新增")
    @PostMapping("save")
    public Result save(@RequestBody Role role){
        boolean save = roleService.save(role);
        if(!save){
            return Result.fail("新增失败！");
        }
        return Result.ok("新增成功！");
    }
    @ApiOperation("角色删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id){
        boolean b = roleService.removeById(id);
        if(!b){
            return Result.fail("删除失败！");
        }
        return Result.ok("删除成功！");
    }
    @ApiOperation("管理员批量删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove( @RequestBody List<String> ids){
        boolean b = roleService.removeByIds(ids);
        if(!b){
            return Result.fail("删除失败！");
        }
        return Result.ok("删除成功！");
    }
    @ApiOperation("角色修改")
    @PutMapping("update")
    public Result update(@RequestBody Role role){
        boolean b = roleService.updateById(role);
        if(!b){
            return Result.fail("修改失败！");
        }
        return Result.ok("修改成功！");
    }
    @ApiOperation("通过id查询角色信息")
    @GetMapping("getId/{id}")
    public Result getRoleId(@PathVariable String id){
        Role role= roleService.getById(id);
        return Result.ok(role);
    }
    @ApiOperation("分页条件查询管理员")
    @GetMapping("{page}/{limit}")
    public Result getPage(@PathVariable Long page,
                          @PathVariable Long limit,
                          RoleQueryVo roleQueryVo){
        Page<Role> pagePara = new Page<>(page, limit);
        IPage<Role> admins= roleService.getPageAdmins(pagePara,roleQueryVo);
        return Result.ok(admins);
    }

}