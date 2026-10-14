package com.yzk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzk.common.result.Result;
import com.yzk.model.acl.Admin;
import com.yzk.service.AdminService;
import com.yzk.service.RoleService;
import com.yzk.utils.MD5;
import com.yzk.vo.acl.AdminQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @description: 管理员管理控制器
 * @date: 2026-10-04 19:18
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Api(tags="管理员管理")
@RestController()
@RequestMapping("/admin/acl/user")
//@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    @ApiOperation("管理员新增")
    @PostMapping("save")
    public Result save(@RequestBody Admin admin){
        //密码加密处理
        admin.setPassword(MD5.encrypt(admin.getPassword()));
        boolean save = adminService.save(admin);
        if(!save){
            return Result.fail("新增失败！");
        }
        return Result.ok("新增成功！");
    }
    @ApiOperation("管理员删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id){
        boolean b = adminService.removeById(id);
        if(!b){
            return Result.fail("删除失败！");
        }
        return Result.ok("删除成功！");
    }
    @ApiOperation("管理员批量删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody ArrayList<Long> ids){
        boolean b = adminService.removeByIds(ids);
        if(!b){
            return Result.fail("删除失败！");
        }
        return Result.ok("删除成功！");
    }
    @ApiOperation("管理员修改")
    @PutMapping("update")
    public Result update(@RequestBody Admin admin){
        boolean b = adminService.updateById(admin);
        if(!b){
            return Result.fail("修改失败！");
        }
        return Result.ok("修改成功！");
    }
    @ApiOperation("通过id查询管理员")
    @GetMapping("getId/{id}")
    public Result getAdminId(@PathVariable String id){
        Admin admin= adminService.getById(id);
        return Result.ok(admin);
    }
    @ApiOperation("分页条件查询管理员")
    @GetMapping("{page}/{limit}")
    public Result getPage(@PathVariable Long page,
                          @PathVariable Long limit,
                          AdminQueryVo adminQueryVo){
        Page<Admin> pagePara = new Page<>(page, limit);
        IPage<Admin> admins= adminService.getPageAdmins(pagePara,adminQueryVo);
        return Result.ok(admins);
    }
    @ApiOperation("为用户进行角色分配")
    @PostMapping("doAssign")
    public Result doAssign(@RequestParam Long adminId,
                           @RequestParam Long[] roleId) {
        roleService.saveAdminRole(adminId,roleId);
        return Result.ok(null);
    }

    @ApiOperation("获取用户角色")
    @GetMapping("toAssign/{adminId}")
    public Result toAssign(@PathVariable Long adminId) {
        //返回map集合包含两部分数据：所有角色 和 为用户分配角色列表
        Map<String,Object> map  = roleService.getRoleByAdminId(adminId);
        return Result.ok(map);
    }


}