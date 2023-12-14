package com.xinshijie.gallery.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson2.JSONObject;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.*;
import com.xinshijie.gallery.dto.PayAlbumDto;
import com.xinshijie.gallery.dto.PayOrderDto;
import com.xinshijie.gallery.enmus.PaymentStatuEnum;
import com.xinshijie.gallery.enmus.VipLongTypeEnum;
import com.xinshijie.gallery.enmus.VipPriceEnum;
import com.xinshijie.gallery.service.*;
import com.xinshijie.gallery.vo.PayOrderVo;
import com.xinshijie.gallery.vo.PayPalTransactionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Scanner;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;
import static com.xinshijie.gallery.util.RequestContextUtil.getUserName;

@Service
public class PaypalServiceImpl implements IPaypalService {

    @Autowired
    private IUserAlbumService userAlbumService;
    @Autowired
    private ISystemUserService systemUserService;
    @Autowired
    private IUserVipService userVipService;
    @Autowired
    private IUserSettingVipService settingVipService;

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.api.base.url}")
    private String baseUrl;


    /**
     * 获取token
     *
     * @return
     * @throws Exception
     */
    public String generateAccessToken() {

        String authString = clientId + ":" + clientSecret;
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/v1/oauth2/token"))
                .header("Authorization", "Basic " + encodedAuthString)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        String token = "";
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Access Token: " + response.body());
            JSONObject jsonObject = JSONObject.parseObject(response.body());
            System.out.println("Access Token: " + jsonObject.get("access_token"));
            token = jsonObject.get("access_token").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 假设响应体是JSON格式并包含access_token字段
        // 在实际应用中，您可能需要解析这个JSON以获取access_token
        return token;
    }

    /**
     * 创建订单
     *
     * @param token
     * @throws IOException
     */
    public PayOrderVo createOrder(String token, PayOrderDto orderDto, String requstId) {
        PayOrderVo payOrderVo = null;
        try {
            //请求id,唯一
            URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");

            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("PayPal-Request-Id", requstId);
            httpConn.setRequestProperty("Authorization", "Bearer " + token);

            httpConn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write(JSONObject.toJSONString(orderDto));
            writer.flush();
            writer.close();
            httpConn.getOutputStream().close();

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            System.out.println(response);
            payOrderVo = JSONObject.parseObject(response, PayOrderVo.class);
            System.out.println(JSONObject.toJSONString(payOrderVo));
        } catch (Exception exception) {
            exception.getMessage();
        }
        return payOrderVo;
    }

    /**
     * 捕获创建订单的付款以完成交易。
     *
     * @param token
     * @param orderId
     * @throws IOException
     */
    public PayPalTransactionVo checkoutOrdersCapture(String token, String orderId, String requestId) {
        try {
            URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders/" + orderId + "/capture");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestMethod("POST");

            httpConn.setRequestProperty("PayPal-Request-Id", requestId);
            httpConn.setRequestProperty("Authorization", "Bearer " + token);

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            System.out.println(response);
            PayPalTransactionVo transactionVo = JSONObject.parseObject(response, PayPalTransactionVo.class);
            System.out.println(JSONObject.toJSONString(transactionVo));
            return transactionVo;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取订单详细
     *
     * @param token
     * @param orderId
     * @throws IOException
     */
    public void getDetail(String token, String orderId) throws IOException {
        URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders/" + orderId);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        httpConn.setRequestProperty("Authorization", "Bearer " + token);

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        System.out.println(response);

    }

    public Double getAmount(PayAlbumDto albumDto){
        /**
         * 物品类别 1 网站会员，2 用户会员 3 网站消费 4.用户图集
         */
        if(albumDto.getKind() == 1 ) {
            Double amount = VipPriceEnum.getPriceByCode(albumDto.getProductId());
            return amount;
        }
        Double discount=1.0;
        SystemUser systemUser=systemUserService.getById(getUserId());
        //获取折扣价格
        if(systemUser.getVip()!=null && systemUser.getVipExpirationTime() != null && systemUser.getVip()>0) {
            Integer vip = systemUser.getVip();
            //判断是否过期
            if(systemUser.getVipExpirationTime().isAfter(LocalDateTime.now())){
                discount=VipPriceEnum.getDiscountByCode(vip);
            }
        }
        //获取用户购买的用户vip信息

        if(albumDto.getKind() == 2 ) {
            UserSettingVip userVip = settingVipService.getById(albumDto.getProductId());
            if (userVip ==null) {
                throw new ServiceException(ResultCodeEnum.DATA_NOT_FOUND);
            }else{
                albumDto.setIncomeUserId(userVip.getUserId());
                Double amount=getProduct(discount,userVip.getPrice());
                return amount;
            }
        }

        if(albumDto.getKind() == 3 ) {
            throw new ServiceException(ResultCodeEnum.DATA_NOT_FOUND);
        }

        if(albumDto.getKind() == 4 ) {
            UserAlbum userAlbum = userAlbumService.getById(albumDto.getProductId());
            //获取VIP信息
            UserVip userVip=userVipService.getInfo(getUserId(),userAlbum.getUserId());
            if (userAlbum ==null) {
                throw new ServiceException(ResultCodeEnum.DATA_NOT_FOUND);
            }else{
                albumDto.setIncomeUserId(userAlbum.getUserId());
                //VIP免费
                if(userAlbum.getCharge()==2){
                    if(userVip==null) {
                        Double amount = getProduct(discount, userAlbum.getPrice());
                        return amount;
                    }else{
                        return 0.0;
                    }
                }

                if(userAlbum.getCharge()==3){
                    if(userVip==null) {
                        Double amount = getProduct(discount, userAlbum.getPrice());
                        return amount;
                    }else{
                        Double amount = getProduct(discount, userAlbum.getVipPrice());
                        return amount;
                    }
                }
                if(userAlbum.getCharge()==4){
                    if(userVip==null) {
                        return 0.0;
                    }else{
                        Double amount = getProduct(discount, userAlbum.getVipPrice());
                        return amount;
                    }
                }
                if(userAlbum.getCharge()==5){
                    Double amount = getProduct(discount, userAlbum.getPrice());
                    return amount;
                }
            }
        }
        return 0.0;
    }

    /**
     * 计算两个 double 类型值的乘积并保留两位
     * @param num1
     * @param num2
     * @return
     */
    public Double getProduct(Double num1,Double num2){
        BigDecimal bd1 = new BigDecimal(Double.toString(num1));
        BigDecimal bd2 = new BigDecimal(Double.toString(num2));
        BigDecimal result = bd1.multiply(bd2).setScale(2, RoundingMode.HALF_UP);

        System.out.println(result.doubleValue());
        return result.doubleValue();
    }

    public void update(PaymentOrder paymentOrder){
        /**
         * 物品类别 1 网站会员，2 用户会员 3 网站消费 4.用户图集
         */
        if(paymentOrder.getKind() == 1 ) {
            SystemUser systemUser=systemUserService.getById(paymentOrder.getUserId());
            systemUser.setId(paymentOrder.getProductId());
            LocalDateTime now=LocalDateTime.now();
            if(systemUser.getVipExpirationTime()==null ||now.isAfter(systemUser.getVipExpirationTime())) {
                now=LocalDateTime.now();
            }else{
                now=systemUser.getVipExpirationTime();
            }
            if(VipPriceEnum.MONTH.getCode().equals(paymentOrder.getProductId())){
                systemUser.setVip(VipPriceEnum.MONTH.getCode());
                systemUser.setVipExpirationTime( LocalDateTimeUtil.offset(now, 1, ChronoUnit.MONTHS));
            }
            if(VipPriceEnum.QUARTER.getCode().equals(paymentOrder.getProductId())){
                systemUser.setVip(VipPriceEnum.QUARTER.getCode());
                systemUser.setVipExpirationTime( LocalDateTimeUtil.offset(now, 3, ChronoUnit.MONTHS));

            }
            if(VipPriceEnum.HALF_YEAR.getCode().equals(paymentOrder.getProductId())){
                systemUser.setVip(VipPriceEnum.HALF_YEAR.getCode());
                systemUser.setVipExpirationTime( LocalDateTimeUtil.offset(now, 6, ChronoUnit.MONTHS));

            }
            if(VipPriceEnum.YEAR.getCode().equals(paymentOrder.getProductId())){
                systemUser.setVip(VipPriceEnum.YEAR.getCode());
                systemUser.setVipExpirationTime( LocalDateTimeUtil.offset(now, 1, ChronoUnit.YEARS));

            }
            if(VipPriceEnum.FOREVER.getCode().equals(paymentOrder.getProductId())){
                systemUser.setVip(VipPriceEnum.FOREVER.getCode());
                systemUser.setVipExpirationTime( LocalDateTimeUtil.offset(now, 99, ChronoUnit.YEARS));
            }
            systemUserService.updateById(systemUser);
        }
        if(paymentOrder.getKind() == 2 ) {
            UserSettingVip settingVip=settingVipService.getById(paymentOrder.getProductId());
            UserVip userVip=userVipService.getInfo(paymentOrder.getUserId(),settingVip.getUserId());
            userVip.setTitle(settingVip.getTitle());
            userVip.setRank(settingVip.getRank());
            userVip.setVid(paymentOrder.getProductId());
            userVip.setUserId(getUserId());
            userVip.setUserName(getUserName());
            userVip.setVipUserId(settingVip.getUserId());
            userVip.setVipUserName(settingVip.getUserName());
            if(userVip==null){
                LocalDateTime now=LocalDateTime.now();
                userVip.setCreateTime(LocalDateTime.now());
                setTime(now,userVip,settingVip);
                userVipService.save(userVip);
            }else {
                LocalDateTime now=LocalDateTime.now();
                userVip.setUpdateTime(LocalDateTime.now());

                if(userVip.getExpirationTime()==null ||now.isAfter(userVip.getExpirationTime())) {
                    now=LocalDateTime.now();
                }else{
                    now=userVip.getExpirationTime();
                }
                setTime(now,userVip,settingVip);
                userVipService.updateById(userVip);
            }
        }
        if(paymentOrder.getKind() == 3 ) {

        }
        if(paymentOrder.getKind() == 4 ) {
            UserAlbum userAlbum=userAlbumService.getById(paymentOrder.getProductId());

        }
    }

    public void setTime(LocalDateTime time,UserVip userVip,UserSettingVip settingVip){
        if(VipLongTypeEnum.DAY.getCode().equals(settingVip.getTimeType())){
            userVip.setExpirationTime( LocalDateTimeUtil.offset(time, settingVip.getTimeLong(), ChronoUnit.DAYS));
        }
        if(VipLongTypeEnum.WEEK.getCode().equals(settingVip.getTimeType())){
            userVip.setExpirationTime( LocalDateTimeUtil.offset(time, settingVip.getTimeLong(), ChronoUnit.WEEKS));
        }
        if(VipLongTypeEnum.MONTH.getCode().equals(settingVip.getTimeType())){
            userVip.setExpirationTime( LocalDateTimeUtil.offset(time, settingVip.getTimeLong(), ChronoUnit.MONTHS));
        }
        if(VipLongTypeEnum.YEAR.getCode().equals(settingVip.getTimeType())){
            userVip.setExpirationTime( LocalDateTimeUtil.offset(time, settingVip.getTimeLong(), ChronoUnit.YEARS));
        }
        if(VipLongTypeEnum.FOREVER.getCode().equals(settingVip.getTimeType())){
            userVip.setExpirationTime( LocalDateTimeUtil.offset(time, 99, ChronoUnit.YEARS));
        }
    }

    @Override
    public void updatIncome(Integer userId, Double amount) {
         systemUserService.updatIncome(userId,amount);
    }
}
