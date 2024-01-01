package com.xinshijie.gallery.service;

import com.xinshijie.gallery.domain.PaymentOrder;
import com.xinshijie.gallery.dto.PayAlbumDto;
import com.xinshijie.gallery.dto.PayOrderDto;
import com.xinshijie.gallery.vo.PayOrderVo;
import com.xinshijie.gallery.vo.PayPalTransactionVo;

public interface IPaypalService {

    String generateAccessToken();

    PayOrderVo createOrder(String token, PayOrderDto orderDto, String requstId);

    PayPalTransactionVo checkoutOrdersCapture(String token, String orderId, String requestId);

    /**
     * 获取实付金额
     * @param albumDto
     * @return
     */
    Double getPaidAmount(PayAlbumDto albumDto);

    /**
     * 获取订单金额。未优惠前的
     * @param albumDto
     * @return
     */
    Double getOrderAmount(PayAlbumDto albumDto);

    void update(PaymentOrder paymentOrder);

    Double getProduct(Double num1,Double num2);
    /**
     * 更新用户的收入
     */
    void updateIncome(Integer userId, Double amount);
}
