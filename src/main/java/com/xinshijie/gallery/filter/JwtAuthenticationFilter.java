package com.xinshijie.gallery.filter;

import com.alibaba.fastjson2.JSONObject;
import com.xinshijie.gallery.common.CacheConstants;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.vo.SystemUserVo;
import jakarta.servlet.ServletException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final RedisTemplate<String, String> redisTemplate;
    private final String secretKey;

    public JwtAuthenticationFilter(RedisTemplate<String, String> redisTemplate, String secretKey) {
        this.redisTemplate = redisTemplate;
        this.secretKey = secretKey;
    }


    @Override
    public void doFilter(jakarta.servlet.ServletRequest servletRequest, jakarta.servlet.ServletResponse servletResponse, jakarta.servlet.FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
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
                        }else {
                            throw new ServiceException("过期");
                        }
                    }
                }else{
                    throw new ServiceException("错误");
                }
            } catch (Exception e) {
                // Handle exception (e.g., logging, throw custom exception, etc.)
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

