package com.xinshijie.gallery.filter;

import com.alibaba.fastjson2.JSONObject;
import com.xinshijie.gallery.common.CacheConstants;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.vo.SystemUserVo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
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
        String token = httpServletRequest.getHeader("Authorization");

        if (token != null && !token.isEmpty() && redisTemplate.hasKey(token)) {
            try {

                Claims claims = Jwts.parser()
//                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                System.out.println("ID: " + claims.getId());
                System.out.println("Subject: " + claims.getSubject());
                System.out.println("Issuer: " + claims.getIssuer());
                System.out.println("Expiration: " + claims.getExpiration());

                // Add user info to the request attribute or header
                String userInfo = claims.getSubject(); // Or any other user identifying info
                httpServletRequest.setAttribute("userInfo", userInfo); // Or use custom header
                SystemUserVo systemUserVo= JSONObject.parseObject(claims.getSubject(),SystemUserVo.class);
                if(systemUserVo!=null){
                    if(redisTemplate.hasKey(CacheConstants.LOGIN_TOKEN_KEY+systemUserVo.getId())){
                        String oldToken=redisTemplate.opsForValue().get(CacheConstants.LOGIN_TOKEN_KEY+systemUserVo.getId());
                        if(oldToken.equals(token)) {
                            httpServletRequest.setAttribute("userId",systemUserVo.getId() );
                            httpServletRequest.setAttribute("userName",systemUserVo.getName() );
                            filterChain.doFilter(servletRequest, servletResponse);
                        }else {
                            sendErrorResponse(httpServletResponse, ResultCodeEnum.EXPIRED);
                        }
                    }
                }else{
                    sendErrorResponse(httpServletResponse, ResultCodeEnum.EXPIRED);
                }
            } catch (Exception e) {
                sendErrorResponse(httpServletResponse, ResultCodeEnum.EXPIRED);

            }
        }else {
            sendErrorResponse(httpServletResponse, ResultCodeEnum.EXPIRED);

        }

    }

    private void sendErrorResponse(HttpServletResponse response, ResultCodeEnum resultCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 更合适的状态码
        response.setContentType("application/json");
        Result<Object> result = Result.error(resultCode);
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}

