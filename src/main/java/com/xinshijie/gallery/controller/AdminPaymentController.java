package com.xinshijie.gallery.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.xinshijie.gallery.common.RedisCache;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.dto.AmountDto;
import com.xinshijie.gallery.dto.PayAlbumDto;
import com.xinshijie.gallery.dto.PayOrderDto;
import com.xinshijie.gallery.dto.PurchaseUnitDto;
import com.xinshijie.gallery.service.*;
import com.xinshijie.gallery.vo.PayOrderVo;
import com.xinshijie.gallery.vo.PayPalTransactionVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;


@Slf4j
@RestController
@RequestMapping("/admin/payments")
public class AdminPaymentController {

    public static final String WebhookId = "0LT105529A2930224";
    @Autowired
    private IPaypalService payPalService;

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
    public Result<PayOrderVo> createPayment(@RequestBody PayAlbumDto albumDto) {
        log.info("-----createPayment----", JSONObject.toJSONString(albumDto));
        Double amount= payPalService.getAmount( albumDto);
        if(amount<=0.0 || !albumDto.equals(amount)){
            throw new ServiceException(ResultCodeEnum.PRICE_ERROR);
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
        purchaseUnit.setReference_id(IdUtil.fastUUID());
        purchaseUnit.setCustom_id("user_id");
        purchaseUnit.setDescription("这是关于订单的说明");
        purchaseUnit.setSoft_descriptor("购买摄影");
        List<PurchaseUnitDto> list = new ArrayList<>();
        list.add(purchaseUnit);
        dto.setPurchase_units(list);
//        String requstId= IdUtil.getSnowflakeNextId()+"";
        //请求，防止重复交易  6 hours.
        String requstId = "1JG72609XE641194A001_" + albumDto.getAid();
        PayOrderVo payOrderVo = payPalService.createOrder(token, dto, requstId);
        if (payOrderVo.getId() != null) {
            redisCache.setCacheString("payment:alubm:" + payOrderVo.getId(), requstId, 1, TimeUnit.HOURS);
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
            return Result.success(transactionVo);
        }
        return Result.success(null);
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

    @PostMapping("/getAmount")
    public Result<Double> getAmount(@RequestBody PayAlbumDto albumDto) {
        return Result.success(payPalService.getAmount(albumDto));
    }
}

