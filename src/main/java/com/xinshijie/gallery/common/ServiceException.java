package com.xinshijie.gallery.common;


import java.io.Serial;

public class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer code;

    public ServiceException(ResultCodeEnum resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    public ServiceException(RespCodeEnum resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    public ServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public ServiceException(String msg, Integer code) {
        super(msg);
        this.code = code;
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    public ServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }
}
