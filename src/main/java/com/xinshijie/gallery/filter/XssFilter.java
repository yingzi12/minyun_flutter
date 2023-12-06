//package com.xinshijie.gallery.filter;
//
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 防止XSS攻击的过滤器
// *
// * @author xinshijie
// */
//@Slf4j
//public class XssFilter implements Filter {
//    /**
//     * 排除链接
//     */
//    public List<String> excludes = new ArrayList<>();
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        String tempExcludes = filterConfig.getInitParameter("excludes");
//        if (StringUtils.isNotEmpty(tempExcludes)) {
//            String[] url = tempExcludes.split(",");
//            for (int i = 0; url != null && i < url.length; i++) {
//                excludes.add(url[i]);
//            }
//        }
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        log.debug("Default charset: " + response.getCharacterEncoding());
//        log.debug("ContentType " + request.getContentType());
//        log.debug("getRemoteHost " + request.getRemoteHost());
//        log.debug("getRemoteAddr " + request.getRemoteAddr());
//        log.debug("getMethod " + ((HttpServletRequest) request).getMethod());
//        log.debug("getRequestURI " + ((HttpServletRequest) request).getRequestURI());
//        log.debug("getServletPath " + ((HttpServletRequest) request).getServletPath());
//        log.debug("getHeaderNames " + ((HttpServletRequest) request).getHeaderNames());
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse resp = (HttpServletResponse) response;
//        String contentType = req.getHeader("Content-Type");
//
//        if (handleExcludeURL(req, resp) || contentType == null || contentType.startsWith("multipart")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        XssBodyHttpServletRequestWrapper xssRequest = new XssBodyHttpServletRequestWrapper((HttpServletRequest) request);
//        chain.doFilter(xssRequest, response);
//    }
//
//    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
//        String url = request.getServletPath();
//        String method = request.getMethod();
//        // GET DELETE 不过滤
//        if (method == null || HttpMethod.GET.matches(method) || HttpMethod.DELETE.matches(method)) {
//            return true;
//        }
//        return StringUtils.matches(url, excludes);
//    }
//
//
//}