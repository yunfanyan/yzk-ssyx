package com.yzk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzk.model.acl.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description: 权限数据访问接口
 * @author: yzk
 * @date: 2026-10-04 23:55
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Mapper
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

}