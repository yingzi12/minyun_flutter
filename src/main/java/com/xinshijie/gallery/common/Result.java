package com.xinshijie.gallery.common;


import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
    private Integer total;


    public Result() {
    }

    private Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(ResultCodeEnum ResultCodeEnum) {
        if (ResultCodeEnum != null) {
            this.code = ResultCodeEnum.getCode();
            this.msg = ResultCodeEnum.getMsg();
        }

    }

    private Result(RespCodeEnum respCodeEnum) {
        if (respCodeEnum != null) {
            this.code = respCodeEnum.getCode();
            this.msg = respCodeEnum.getMsg();
        }

    }

    public T result() {
        if (ResultCodeEnum.SUCCESS.getCode() != this.code) {
            throw new ServiceException(this.code, this.msg);
        } else {
            return this.data;
        }
    }

    public boolean isSucc() {
        return this.code != null && ResultCodeEnum.SUCCESS.getCode() == this.code;
    }

    public boolean isFail() {
        return !this.isSucc();
    }

    private Result(T data) {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.msg = ResultCodeEnum.SUCCESS.getMsg();
        this.data = data;
    }

    private Result(T data, Integer total) {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.msg = ResultCodeEnum.SUCCESS.getMsg();
        this.data = data;
        this.total = total;
    }

    public static <T> Result<T> success(T data) {
        return new Result(data);
    }
    public static <T> Result<T> success(ResultCodeEnum ResultCodeEnum) {
        return new Result(ResultCodeEnum);
    }

    public static <T> Result<T> success(T data, Integer total) {
        return new Result(data, total);
    }

    public static Result<String> success() {
        return new Result(ResultCodeEnum.SUCCESS);
    }

    public static Result<String> success(Integer code, String msg) {
        return new Result(code, msg);
    }

    public static <T> Result<T> error(ResultCodeEnum ResultCodeEnum) {
        return new Result(ResultCodeEnum);
    }

    public static <T> Result<T> error(RespCodeEnum respCodeEnum) {
        return new Result(respCodeEnum);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result(code, msg);
    }


}


