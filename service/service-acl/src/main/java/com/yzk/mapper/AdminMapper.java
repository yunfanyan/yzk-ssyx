package com.yzk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzk.model.acl.Admin;
import com.yzk.vo.acl.AdminQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @description: 管理员业务实现接口
 * @author: yzk
 * @date: 2026-10-04 19:23
 * @version: 1.0
 * @email: yzk@qq.com
 */
@Mapper
@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 分页条件查询管理员信息
     * @param pagePara 分页条件
     * @param adminQueryVo 查询条件
     * @return
     */
    IPage<Admin> pageList(Page<Admin> pagePara, @Param("vo") AdminQueryVo adminQueryVo);
}