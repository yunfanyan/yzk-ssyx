package com.yzk.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzk.model.acl.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description: 角色权限关系数据访问类
 * @author: yzk
 * @date: 2026-10-05 10:20
 * @version: 1.0
 * @email: yzk@qq.com
 */

@Mapper
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

}