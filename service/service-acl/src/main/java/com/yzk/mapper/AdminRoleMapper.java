package com.yzk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzk.model.acl.AdminRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description: 接口功能描述
 * @author: yzk
 * @date: 2026-10-04 23:08
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Mapper
@Repository
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

}