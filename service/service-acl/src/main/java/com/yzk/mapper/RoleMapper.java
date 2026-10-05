package com.yzk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzk.model.acl.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description: 角色数据访问接口
 * @author: yzk
 * @date: 2026-10-04 21:27
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role>{

}