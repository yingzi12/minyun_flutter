package com.xinshijie.gallery;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.xinshijie.gallery.dto.AmountDto;
import com.xinshijie.gallery.dto.PayOrderDto;
import com.xinshijie.gallery.dto.PurchaseUnitDto;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
@Slf4j
class PaypalMain {

    private static final String PAYPAL_CLIENT_ID = "AWwAGKZhvPE3xSgDh-gRH9sXwNMKDQSzr65ZwaUHp-U7CTbUk-FTnRRjlF0zTpz5LaeDz5rHgcaaekVm";
    private static final String PAYPAL_CLIENT_SECRET = "ECmBhtDZKC-HCsF_4jeWuZk3uiuaicf0k0IU38O8ivuZJFgvgs_5lfS14DvPdtme5FIXyodxj4jclVWe";
    private static final String PAYPAL_API_BASE = "https://api-m.sandbox.paypal.com";

    public static void main(String[] args) throws IOException {
        String token = "";
        try {
            String accessToken = generateAccessToken();
//            System.out.println("Access Token: " + accessToken);
            JSONObject jsonObject = JSONObject.parseObject(accessToken);
//            System.out.println("Access Token: " + jsonObject.get("access_token"));
            token = jsonObject.get("access_token").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PayOrderDto dto = new PayOrderDto();
        dto.setIntent("CAPTURE");
        AmountDto amount = new AmountDto();
        amount.setValue(0.3 + "");
        PurchaseUnitDto purchaseUnit = new PurchaseUnitDto();
        purchaseUnit.setAmount(amount);
        purchaseUnit.setReference_id(IdUtil.fastUUID());
        purchaseUnit.setCustom_id("user_id");
        purchaseUnit.setDescription("这是一个说明");
        List<PurchaseUnitDto> list = new ArrayList<>();
        list.add(purchaseUnit);
        dto.setPurchase_units(list);
//        String requstId= IdUtil.getSnowflakeNextId()+"";
        String requstId = "1JG72609XE641194A";
//        createOrder(requstId, token, dto);
        //"0XP707945N772224R"
        getDetail(token,"4S7935240W938531R");
//        checkoutOrdersCapture(token,"7LH93536HU642170T");
    }

    /**
     * 获取token
     *
     * @return
     * @throws Exception
     */
    private static String generateAccessToken() throws Exception {
        if (PAYPAL_CLIENT_ID.isEmpty() || PAYPAL_CLIENT_SECRET.isEmpty()) {
            throw new IllegalArgumentException("MISSING_API_CREDENTIALS");
        }

        String authString = PAYPAL_CLIENT_ID + ":" + PAYPAL_CLIENT_SECRET;
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_BASE + "/v1/oauth2/token"))
                .header("Authorization", "Basic " + encodedAuthString)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 假设响应体是JSON格式并包含access_token字段
        // 在实际应用中，您可能需要解析这个JSON以获取access_token
        return response.body();
    }

    /**
     * 确认订单
     *
     * @param token
     * @param orderId
     * @throws IOException
     */
    public static void confirmOrder(String token, String orderId) throws IOException {
        URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders/" + orderId + "/confirm-payment-source");
                                //https://api.sandbox.paypal.com/v2/checkout/orders/7LH93536HU642170T/capture
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("Authorization", "Bearer " + token);

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("{ \"payment_source\": { \"paypal\": { \"name\": { \"given_name\": \"John\", \"surname\": \"Doe\" }, \"email_address\": \"customer@example.com\", \"experience_context\": { \"payment_method_preference\": \"IMMEDIATE_PAYMENT_REQUIRED\", \"brand_name\": \"EXAMPLE INC\", \"locale\": \"en-US\", \"landing_page\": \"LOGIN\", \"shipping_preference\": \"SET_PROVIDED_ADDRESS\", \"user_action\": \"PAY_NOW\", \"return_url\": \"https://example.com/returnUrl\", \"cancel_url\": \"https://example.com/cancelUrl\" } } } }");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        System.out.println(response);
    }


    /**
     * 创建订单
     *
     * @param token
     * @throws IOException
     */
    public static void createOrder(String requstId, String token, PayOrderDto orderDto) throws IOException {
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
        log.info(response);
//        PayOrderVo payOrderVo = JSONObject.parseObject(response, PayOrderVo.class);
//        System.out.println(JSONObject.toJSONString(payOrderVo));
    }

    /**
     * 授权订单付款
     *
     * @param token
     * @param orderId
     * @throws IOException
     */
    public static void checkoutOrdersAuthorize(String token, String orderId) throws IOException {
        URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders/" + orderId + "/authorize");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("PayPal-Request-Id", "7b92603e-77ed-4896-8e78-5dea2050476a");
        httpConn.setRequestProperty("Authorization", "Bearer " + token);

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        System.out.println(response);
    }

    public static void checkoutOrdersCapture(String token,String orderId) throws IOException {
        URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders/"+orderId+"/capture");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("PayPal-Request-Id", "111111111");
        httpConn.setRequestProperty("Authorization", "Bearer " + token);

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        System.out.println(response);
    }

    /**
     * 获取订单详细
     *
     * @param token
     * @param orderId
     * @throws IOException
     */
    public static void getDetail(String token, String orderId) throws IOException {
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

//    public static void main(String[] args) throws IOException {
//        String token = "";
//        try {
//            String accessToken = generateAccessToken();
//            System.out.println("Access Token: " + accessToken);
//            JSONObject jsonObject = JSONObject.parseObject(accessToken);
//            System.out.println("Access Token: " + jsonObject.get("access_token"));
//            token = jsonObject.get("access_token").toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders/7LH93536HU642170T/capture");
//        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//        httpConn.setRequestMethod("POST");
//
//        httpConn.setRequestProperty("PayPal-Request-Id", "7b92603e-77ed-4896-8e78-5dea2050476a");
//        httpConn.setRequestProperty("Authorization", "Bearer "+token);
//
//        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
//                ? httpConn.getInputStream()
//                : httpConn.getErrorStream();
//        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
//        String response = s.hasNext() ? s.next() : "";
//        System.out.println(response);
//    }
}
