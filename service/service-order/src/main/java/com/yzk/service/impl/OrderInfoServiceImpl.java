package com.yzk.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzk.mapper.OrderInfoMapper;
import com.yzk.model.order.OrderInfo;
import com.yzk.service.OrderInfoService;
import com.yzk.vo.order.OrderConfirmVo;
import com.yzk.vo.order.OrderSubmitVo;
import com.yzk.vo.order.OrderUserQueryVo;
import org.springframework.stereotype.Service;

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
        return null;
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
