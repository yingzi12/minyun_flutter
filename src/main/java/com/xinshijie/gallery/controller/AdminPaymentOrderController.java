package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.PaymentOrder;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.dto.PaymentOrderDto;
import com.xinshijie.gallery.enmus.PaymentStatuEnum;
import com.xinshijie.gallery.service.IPaymentOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin/paymentOrder")
public class AdminPaymentOrderController {
    @Autowired
    private IPaymentOrderService paymentOrderService;

    @GetMapping("listSell")
    public Result<List<PaymentOrder>> listSell( PaymentOrderDto findDto){
        findDto.setIncomeUserId(getUserId());
        IPage<PaymentOrder> vo = paymentOrderService.getList(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }
    @GetMapping("listBuy")
    public Result<List<UserAlbum>> listBuy( PaymentOrderDto findDto){
        findDto.setUserId(getUserId());
        ArrayList kind=new ArrayList();
        kind.add(4);
        IPage<UserAlbum> vo = paymentOrderService.listBuy(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }
    @GetMapping("listLog")
    public Result<List<PaymentOrder>> listLog( PaymentOrderDto findDto){
        findDto.setUserId(getUserId());
        IPage<PaymentOrder> vo = paymentOrderService.getList(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }

    @GetMapping("info")
    public Result<PaymentOrder> getInfo(@RequestParam("id")Integer id,@RequestParam("kind")Integer kind,@RequestParam("productId")Integer productId){
        Integer userId = getUserId();
        QueryWrapper<PaymentOrder> qw=new QueryWrapper<>();
        qw.eq("user_id",userId);
        qw.eq("kind", kind);
        qw.eq("status", PaymentStatuEnum.DONE.getCode());
        qw.eq("product_id", productId);
        PaymentOrder vo = paymentOrderService.getOne(qw);

        return Result.success(vo);
    }
}
