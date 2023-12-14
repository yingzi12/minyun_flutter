package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.PaymentOrder;
import com.xinshijie.gallery.dto.PaymentOrderDto;
import com.xinshijie.gallery.enmus.PaymentStatuEnum;
import com.xinshijie.gallery.enmus.VipPriceEnum;
import com.xinshijie.gallery.mapper.PaymentOrderMapper;
import com.xinshijie.gallery.service.IPaymentOrderService;
import com.xinshijie.gallery.vo.PaymentOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class PaymentOrderServiceImpl extends ServiceImpl<PaymentOrderMapper, PaymentOrder> implements IPaymentOrderService {

    @Autowired
    private PaymentOrderMapper mapper;



    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<PaymentOrder> getPagePaymentOrder(PaymentOrder dto) {
        Page<PaymentOrder> page = new Page<>();
//        page.setCurrent(dto.getPageNum());
//        page.setSize(dto.getPageSize());
        QueryWrapper<PaymentOrder> qw = new QueryWrapper<>();
        return mapper.selectPage(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public PaymentOrder add(PaymentOrder dto) {
        PaymentOrder value = new PaymentOrder();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(PaymentOrder dto) {
        return mapper.updateById(dto);
    }

    /**
     * 删除数据
     */
    @Override
    public Integer delById(Long id) {
        return mapper.deleteById(id);
    }

    /**
     * 根据id数据
     */
    @Override
    public PaymentOrder getInfo(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public PaymentOrder selectByPayId(String payId) {
        QueryWrapper<PaymentOrder> qw=new QueryWrapper<>();
        qw.eq("pay_id",payId);
        return mapper.selectOne(qw);
    }

    @Override
    public PaymentOrder selectWaitPay(Integer userId, Integer kind, Integer productId) {
        QueryWrapper<PaymentOrder> qw=new QueryWrapper<>();
        qw.eq("user_id",userId);
        qw.eq("kind",kind);
        qw.eq("product_id",productId);
        qw.le("expired_time", LocalDateTime.now());
        qw.le("status", PaymentStatuEnum.WAIT.getCode());
        return mapper.selectOne(qw);
    }
    @Override
    public PaymentOrder selectByDonePay(Integer userId, Integer kind, Integer productId) {
        QueryWrapper<PaymentOrder> qw=new QueryWrapper<>();
        qw.eq("user_id",userId);
        qw.eq("kind",kind);
        qw.eq("product_id",productId);
        qw.le("status", PaymentStatuEnum.DONE.getCode());
        return mapper.selectOne(qw);
    }
}
