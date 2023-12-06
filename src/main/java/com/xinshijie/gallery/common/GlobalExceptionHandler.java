package com.xinshijie.gallery.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author xinshijie
 */
@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                      HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI,  e.getMessage(),e);
        return Result.error(ResultCodeEnum.SYSTEM_INNER_ERROR);
    }

    @ExceptionHandler(NumberFormatException.class)
    public Result handNumberFormatException(NumberFormatException e,
                                                      HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',参数错误'{}'请求", requestURI,  e.getMessage(),e);
        return Result.error(ResultCodeEnum.PARAMS_IS_INVALID);
    }

    @ExceptionHandler(UncategorizedSQLException.class)
    public Result handSQLSyntaxErrorException(UncategorizedSQLException e,
                                            HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',系统内部错误'{}'请求", requestURI, e.getMessage(),e);
        return Result.error(ResultCodeEnum.INTERFACE_INNER_INVOKE_ERROR);
    }
    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public Result handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error(" 业务异常,系统内部错误'{}'请求", e.getMessage(),e);
        Integer code = e.getCode();
        return code != null ? Result.error(code, e.getMessage()) : Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}', {}发生未知异常.", requestURI,  e.getMessage(),e);
        return Result.error(ResultCodeEnum.SYSTEM_INNER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public Result handleException(NullPointerException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',{} 发生系统异常.", requestURI,  e.getMessage(),e);
        return Result.error(ResultCodeEnum.SYSTEM_INNER_ERROR);
    }
    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("系统异常 请求地址'{}',{}发生系统异常.", requestURI,  e.getMessage(),e);
        return Result.error(ResultCodeEnum.SYSTEM_INNER_ERROR);
    }


    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        log.error("自定义验证异常'{}',发生系统异常.",  message,e);
        return Result.error(ResultCodeEnum.SYSTEM_INNER_ERROR);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        log.error("自定义验证异常'{}',发生系统异常.",message,e);
        return Result.error(ResultCodeEnum.SYSTEM_INNER_ERROR);
    }

}
