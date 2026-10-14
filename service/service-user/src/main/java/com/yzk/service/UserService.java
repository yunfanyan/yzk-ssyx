package com.yzk.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.enums.user.User;
import com.yzk.vo.user.LeaderAddressVo;
import com.yzk.vo.user.UserLoginVo;


public interface UserService extends IService<User> {


    User getUserByOpenId(String openid);

    LeaderAddressVo getLeaderAddressByUserId(Long id);

    UserLoginVo getUserLoginVo(Long id);
}
