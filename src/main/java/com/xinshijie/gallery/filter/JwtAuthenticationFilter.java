package com.xinshijie.gallery.filter;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import com.alibaba.fastjson2.JSONObject;
import com.xinshijie.gallery.common.*;
import com.xinshijie.gallery.vo.SystemUserVo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final RedisTemplate<String, String> redisTemplate;
//    private final String secretKey;

    public JwtAuthenticationFilter(RedisTemplate<String, String> redisTemplate, String secretKey) {
        this.redisTemplate = redisTemplate;
//        this.secretKey = secretKey;
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest servletRequest, jakarta.servlet.ServletResponse servletResponse, jakarta.servlet.FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String authHeader = httpServletRequest.getHeader("Authorization");
        String method = httpServletRequest.getMethod();
        if ("OPTIONS".equals(method)) {
            // 处理OPTIONS请求，允许预检请求通过
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*"); // 或者指定允许的域
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
            httpServletResponse.setHeader("Access-Control-Max-Age", "3600"); // 预检请求的缓存时间，以秒为单位
            httpServletResponse.setStatus(HttpServletResponse.SC_OK); // HTTP 200 OK
        } else {
            log.info("获取到token:" + authHeader);
            if (authHeader != null && !authHeader.isEmpty()) {
                try {
                    String token = "";
                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        token = authHeader.substring("Bearer ".length());
                    } else {
                        sendErrorResponse(httpServletResponse, ResultCodeEnum.EXPIRED);
                    }
                    log.info("Token: " + token);
                    boolean validate = JWT.of(token).setKey(Constants.TOKEN_KEY).verify();

                    JWT jwt = JWT.of(token);
                    jwt.getHeader(JWTHeader.TYPE);
                    jwt.getHeader(JWTHeader.ALGORITHM);
                    String userInfo = jwt.getPayload("user").toString();
                    System.out.println("userInfo: " + userInfo);
                    httpServletRequest.setAttribute("userInfo", userInfo); // Or use custom header
                    SystemUserVo systemUserVo = JSONObject.parseObject(userInfo, SystemUserVo.class);
                    if (systemUserVo != null) {
                        if (redisTemplate.hasKey(CacheConstants.LOGIN_TOKEN_KEY + systemUserVo.getId())) {
                            redisTemplate.opsForValue().set(CacheConstants.LOGIN_TOKEN_KEY + systemUserVo.getId(), token, 2, TimeUnit.HOURS);
                            String oldToken = redisTemplate.opsForValue().get(CacheConstants.LOGIN_TOKEN_KEY + systemUserVo.getId());
                            if (oldToken.equals(token)) {
                                httpServletRequest.setAttribute("userId", systemUserVo.getId());
                                httpServletRequest.setAttribute("userName", systemUserVo.getName());
                                filterChain.doFilter(servletRequest, servletResponse);
                            } else {
                                sendErrorResponse(httpServletResponse, ResultCodeEnum.EXPIRED);
                            }
                        }else {
                            sendErrorResponse(httpServletResponse, ResultCodeEnum.EXPIRED);
                        }
                    } else {
                        sendErrorResponse(httpServletResponse, ResultCodeEnum.EXPIRED);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("错误，出现异常，",e);
                    sendErrorResponse(httpServletResponse, ResultCodeEnum.EXPIRED);
                }
            } else {
                sendErrorResponse(httpServletResponse, ResultCodeEnum.EXPIRED);

            }
        }

    }

    private void sendErrorResponse(HttpServletResponse response, ResultCodeEnum resultCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 更合适的状态码
        response.setContentType("application/json");
        Result<Object> result = Result.error(resultCode);
        response.getWriter().write(JSONObject.toJSONString(result));
    }


}

