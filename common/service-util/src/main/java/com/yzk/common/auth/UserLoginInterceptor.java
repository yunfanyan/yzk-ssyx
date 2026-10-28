<<<<<<< HEAD
package com.yzk.common.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzk.common.constant.RedisConst;
import com.yzk.common.result.Result;
import com.yzk.common.result.ResultCodeEnum;
import com.yzk.utils.JwtHelper;
import com.yzk.vo.user.UserLoginVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginInterceptor implements HandlerInterceptor {

    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserLoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("token");

        // 1. 没 token,直接拦
        if (StringUtils.isEmpty(token)) {
            return writeUnauthorized(response, "请先登录");
        }

        // 2. 有 token,查 Redis 里的用户信息
        Long userId = JwtHelper.getUserId(token);
        UserLoginVo userLoginVo = (UserLoginVo) redisTemplate.opsForValue()
                .get(RedisConst.USER_LOGIN_KEY_PREFIX + userId);

        // 3. Redis 里没有 = 登录态过期/被清掉(比如切了 Redis),拦下并提示重新登录
        if (userLoginVo == null) {
            return writeUnauthorized(response, "登录已过期，请重新登录");
        }

        // 4. 正常,放入 ThreadLocal
        AuthContextHolder.setUserId(userLoginVo.getUserId());
        AuthContextHolder.setWareId(userLoginVo.getWareId());
        AuthContextHolder.setUserLoginVo(userLoginVo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        // 请求结束必须清理,防止线程复用串数据
        AuthContextHolder.remove();
    }

    private boolean writeUnauthorized(HttpServletResponse response, String message) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH.getCode(), message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
        return false;
    }
}
=======
>>>>>>> parent of d1424a5 (微信小程序登录界面，首页，分类查询接口完成)
