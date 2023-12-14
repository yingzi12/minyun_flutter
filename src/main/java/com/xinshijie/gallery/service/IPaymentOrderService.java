package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.PaymentOrder;
import com.xinshijie.gallery.dto.PaymentOrderDto;
import com.xinshijie.gallery.vo.PaymentOrderVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-13
 */
public interface IPaymentOrderService extends IService<PaymentOrder> {



    /**
     * 分页查询信息表
     */
    Page<PaymentOrder> getPagePaymentOrder(PaymentOrder dto);

    /**
     * 新增数据
     */
    PaymentOrder add(PaymentOrder id);

    /**
     * 根据id修改数据
     */
    Integer edit(PaymentOrder dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    PaymentOrder getInfo(Long id);

    PaymentOrder selectByPayId(String payId);

    PaymentOrder selectByUserIdKindProductId(Integer userId,Integer kind,Integer productId);

}
