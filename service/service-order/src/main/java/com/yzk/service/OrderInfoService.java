package com.yzk.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzk.model.order.OrderInfo;
import com.yzk.vo.order.OrderConfirmVo;
import com.yzk.vo.order.OrderSubmitVo;
import com.yzk.vo.order.OrderUserQueryVo;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-18
 */
public interface OrderInfoService extends IService<OrderInfo> {
    /**
     * 生成订单信息
     * @param orderParamVo
     * @return
     */
    Long submitOrder(OrderSubmitVo orderParamVo);

    /**
     * 订单条件分页查询
     * @param pageParam
     * @param orderUserQueryVo
     * @return
     */
    IPage<OrderInfo> getOrderInfoByUserIdPage(Page<OrderInfo> pageParam, OrderUserQueryVo orderUserQueryVo);

    /**
     * 确认订单
     * @return
     */
    OrderConfirmVo confirmOrder();

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    OrderInfo getOrderInfoById(Long orderId);

    /**
     * 根据订单no查询订单
     * @param orderNo
     * @return
     */
    OrderInfo getOrderInfoByOrderNo(String orderNo);
}
