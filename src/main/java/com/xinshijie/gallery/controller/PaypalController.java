package com.xinshijie.gallery.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping("/admin/paypal")
public class PaypalController {
    public static final String WebhookId = "4JH86294D6297924G";

    @PostMapping("/webhook")
    public String validatePayPalWebhook(HttpServletRequest request) throws IOException {
        System.out.println("webhook");
//        APIContext apiContext = new APIContext("CLIENT_ID", "CLIENT_SECRET", "MODE");
//        Boolean result = Event.validateReceivedEvent(apiContext, getHeadersInfo(request), getBody(request));
        getHeadersInfo(request);
        getBody(request);
        // 处理验证结果
        return "Webhook Validated: ";
    }

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

    private static String getBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        }
        return stringBuilder.toString();
    }
}
