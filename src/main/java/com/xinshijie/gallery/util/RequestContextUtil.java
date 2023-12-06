package com.xinshijie.gallery.util;

import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestContextUtil {

    private RequestContextUtil() {
        // 私有构造函数，防止实例化
    }

    /**
     * 获取当前请求的 HttpServletRequest 实例。
     * @return 当前的 HttpServletRequest，如果没有绑定请求则返回 null。
     */
    private static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs != null ? attrs.getRequest() : null;
    }

    /**
     * 从当前请求的头部获取 userId。
     * @return userId 字符串，如果没有找到则返回 null。
     */
    public static Integer getUserId() {
        HttpServletRequest request = getCurrentRequest();
        if(request == null || request.getHeader("userId")==null ){
            throw new ServiceException(ResultCodeEnum.EXPIRED);
        }
        return Integer.parseInt(request.getHeader("userId"));
    }

    /**
     * 从当前请求的头部获取 userName。
     * @return userName 字符串，如果没有找到则返回 null。
     */
    public static String getUserName() {
        HttpServletRequest request = getCurrentRequest();
        if(request == null || request.getHeader("username")==null ){
            throw new ServiceException(ResultCodeEnum.EXPIRED);
        }
        return request.getHeader("username") ;
    }
}
