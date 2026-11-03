package com.yzk.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.client.ActivityFeignClient;
import com.yzk.client.CartFeignClient;
import com.yzk.client.ProductFeignClient;
import com.yzk.client.UserFeignClient;
import com.yzk.common.auth.AuthContextHolder;
import com.yzk.common.constant.RedisConst;
import com.yzk.mapper.OrderInfoMapper;
import com.yzk.model.order.CartInfo;
import com.yzk.model.order.OrderInfo;
import com.yzk.service.OrderInfoService;
import com.yzk.vo.order.OrderConfirmVo;
import com.yzk.vo.order.OrderSubmitVo;
import com.yzk.vo.order.OrderUserQueryVo;
import com.yzk.vo.user.LeaderAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-18
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private CartFeignClient cartFeignClient;
    @Autowired
    private ActivityFeignClient activityFeignClient;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 生成订单
     * @param orderParamVo
     * @return
     */
    @Override
    public Long submitOrder(OrderSubmitVo orderParamVo) {
        return 0L;
    }

    /**
     * 订单条件分页查询
     * @param pageParam
     * @param orderUserQueryVo
     * @return
     */
    @Override
    public IPage<OrderInfo> getOrderInfoByUserIdPage(Page<OrderInfo> pageParam, OrderUserQueryVo orderUserQueryVo) {
        return null;
    }

    /**
     * 确认订单
     * @return
     */

    @Override
    public OrderConfirmVo confirmOrder() {
        //获取用户id
        Long userId = AuthContextHolder.getUserId();

        //获取用户对应团长信息
        LeaderAddressVo leaderAddressVo =
                userFeignClient.getUserAddressByUserId(userId);

        //获取购物车里面选中的商品
        List<CartInfo> cartInfoList = cartFeignClient.getCartCheckedList(userId);

        //唯一标识订单
        String orderNo = System.currentTimeMillis()+ "";
        redisTemplate.opsForValue().set(RedisConst.ORDER_REPEAT+orderNo,orderNo,
                24, TimeUnit.HOURS);

        //获取购物车满足条件活动和优惠卷信息
        OrderConfirmVo orderConfirmVo =
                activityFeignClient.findCartActivityAndCoupon(cartInfoList, userId);
        //封装其他值
        orderConfirmVo.setLeaderAddressVo(leaderAddressVo);
        orderConfirmVo.setOrderNo(orderNo);

        return orderConfirmVo;

    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */

    @Override
    public OrderInfo getOrderInfoById(Long orderId) {
        return null;
    }

    /**
     * 根据orderNo查询订单
     * @param orderNo
     * @return
     */
    @Override
    public OrderInfo getOrderInfoByOrderNo(String orderNo) {
        return null;
    }
}
