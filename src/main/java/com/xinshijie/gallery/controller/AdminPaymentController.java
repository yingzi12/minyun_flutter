package com.xinshijie.gallery.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.xinshijie.gallery.common.RedisCache;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.PaymentOrder;
import com.xinshijie.gallery.dto.AmountDto;
import com.xinshijie.gallery.dto.PayAlbumDto;
import com.xinshijie.gallery.dto.PayOrderDto;
import com.xinshijie.gallery.dto.PurchaseUnitDto;
import com.xinshijie.gallery.enmus.PaymentStatuEnum;
import com.xinshijie.gallery.service.*;
import com.xinshijie.gallery.vo.PayOrderVo;
import com.xinshijie.gallery.vo.PayPalTransactionVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;
import static com.xinshijie.gallery.util.RequestContextUtil.getUserName;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin/payments")
public class AdminPaymentController {

    public static final String WebhookId = "0LT105529A2930224";
    @Autowired
    private IPaypalService payPalService;
    @Autowired
    private IPaymentOrderService paymentOrderService;
    @Autowired
    private RedisCache redisCache;

    private static Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    @PostMapping("/create")
    public Result<PayOrderVo> createPayment(@Valid  @RequestBody PayAlbumDto albumDto) {
        log.info("-----createPayment----", JSONObject.toJSONString(albumDto));
        Integer userId=getUserId();
        PaymentOrder paymentOrderDto = paymentOrderService.selectWaitPay(userId,albumDto.getKind(),albumDto.getProductId());
        albumDto.setUserId(userId);
        Double amount=0.0;
        String requstId = IdUtil.fastSimpleUUID();
        if(paymentOrderDto==null){
            amount= payPalService.getPaidAmount( albumDto);
            if(amount<=0.0 || !albumDto.getAmount().equals(amount)){
                throw new ServiceException(ResultCodeEnum.PRICE_ERROR);
            }
        }else {
            requstId=paymentOrderDto.getRequestId();
            amount = paymentOrderDto.getAmount();
        }

        String token = payPalService.generateAccessToken();
//        String requestId= IdUtil.fastSimpleUUID();
        PayOrderDto dto = new PayOrderDto();
        dto.setIntent("CAPTURE");
        AmountDto amountDto = new AmountDto();
        amountDto.setValue(String.valueOf(albumDto.getAmount()));
        PurchaseUnitDto purchaseUnit = new PurchaseUnitDto();
        purchaseUnit.setAmount(amountDto);
        //物品id
        purchaseUnit.setReference_id(getUserId()+"_"+albumDto.getKind()+"_"+albumDto.getProductId());
        purchaseUnit.setCustom_id(getUserId()+"_"+albumDto.getKind()+"_"+albumDto.getProductId());
        purchaseUnit.setDescription(albumDto.getDescription());
        purchaseUnit.setSoft_descriptor(albumDto.getProductName());
        List<PurchaseUnitDto> list = new ArrayList<>();
        list.add(purchaseUnit);
        dto.setPurchase_units(list);
//        String requstId= IdUtil.getSnowflakeNextId()+"";
        //请求，防止重复交易  6 hours.
        PayOrderVo payOrderVo = payPalService.createOrder(token, dto, requstId);
        if (payOrderVo.getId() != null) {
            redisCache.setCacheString("payment:alubm:" + payOrderVo.getId(), requstId, 1, TimeUnit.HOURS);
            PaymentOrder paymentOrder = paymentOrderService.selectByPayId(payOrderVo.getId());
            if(paymentOrder==null){
                paymentOrder=new PaymentOrder();
                paymentOrder.setAmount(amount);
                paymentOrder.setCreateTime(LocalDateTime.now());
                paymentOrder.setKind(albumDto.getKind());
                paymentOrder.setCountry("USD");
                paymentOrder.setPayId(payOrderVo.getId());
                paymentOrder.setUserId(getUserId());
                paymentOrder.setUsername(getUserName());
                paymentOrder.setDescription(albumDto.getDescription());
                paymentOrder.setStatus(PaymentStatuEnum.WAIT.getCode());
                paymentOrder.setProductId(albumDto.getProductId());
                paymentOrder.setProductName(albumDto.getProductName());
                paymentOrder.setRequestId(requstId);
                paymentOrder.setIncomeUserId(albumDto.getIncomeUserId());
                paymentOrder.setExpiredTime( LocalDateTimeUtil.offset(LocalDateTime.now(), 3, ChronoUnit.HOURS));
                Double orderAmount= payPalService.getOrderAmount(albumDto);
                paymentOrder.setPaidAmount(orderAmount);
                paymentOrderService.save(paymentOrder);
            }
        }
        return Result.success(payOrderVo);
    }

    @GetMapping("/ordersCapture")
    public Result<PayPalTransactionVo> checkoutOrdersCapture(@RequestParam("orderId") String orderId) {
        String requstId = redisCache.getCacheString("payment:alubm:" + orderId);
        if (requstId != null) {
            log.info("-----ordersCapture----" + orderId);
            String requestId = IdUtil.fastSimpleUUID();
            String token = payPalService.generateAccessToken();
            PayPalTransactionVo transactionVo = payPalService.checkoutOrdersCapture(token, orderId, requestId);
            PaymentOrder paymentOrder = paymentOrderService.selectByPayId(transactionVo.getId());
            if(paymentOrder!=null){
                paymentOrder.setStatus(PaymentStatuEnum.DONE.getCode());
                paymentOrder.setPayTime(LocalDateTime.now());
                paymentOrderService.updateById(paymentOrder);
                payPalService.update(paymentOrder);
                if(paymentOrder.getIncomeUserId()!=null) {
                    payPalService.updateIncome(paymentOrder.getIncomeUserId(), paymentOrder.getAmount());
                }

            }
            return Result.success(transactionVo);
        }
        return Result.error(ResultCodeEnum.EXPIRED);
    }

    @PostMapping("/webhook")
    public String validateWebhook(HttpServletRequest req, @RequestBody String requestBody) {
        try {
            // ### Api Context
//            APIContext apiContext = new APIContext(clientID, clientSecret, mode);
//            apiContext.addConfiguration(Constants.PAYPAL_WEBHOOK_ID, WebhookId);
//             System.out.println(requestBody);
//            Boolean result = Event.validateReceivedEvent(apiContext, getHeadersInfo(req), requestBody);
            log.info("Webhook Validated: " + requestBody);
            log.info("Webhook getHeadersInfo: " + JSONObject.toJSONString(getHeadersInfo(req)));

            return "Webhook Validated: ";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Webhook Validated: Failed - " + e.getMessage();
        }
    }

    /**
     * 获取实付金额
     * @param albumDto
     * @return
     */
    @PostMapping("/getAmount")
    public Result<Double> getAmount(@RequestBody PayAlbumDto albumDto) {
        PaymentOrder paymentOrderDto = paymentOrderService.selectWaitPay(getUserId(),albumDto.getKind(),albumDto.getProductId());
        if(paymentOrderDto != null){
            return Result.success(paymentOrderDto.getAmount());
        }
        return Result.success(payPalService.getPaidAmount(albumDto));
    }
}

