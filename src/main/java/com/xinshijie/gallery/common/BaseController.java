package com.xinshijie.gallery.common;

import lombok.extern.slf4j.Slf4j;

import static com.xinshijie.gallery.common.ResultCodeEnum.SYSTEM_INNER_ERROR;

/**
 * web层通用数据处理
 *
 * @author xinshijie
 */
@Slf4j
public class BaseController<T> {

    /**
     * 返回成功
     */
    public Result<String> success() {
        return Result.success();
    }

    /**
     * 返回失败消息
     */
    public Result<String> error() {
        return Result.error(SYSTEM_INNER_ERROR);
    }

    /**
     * 返回失败消息
     */
    public Result<String> error(String message) {
        return Result.error(SYSTEM_INNER_ERROR.getCode(), message);
    }

    /**
     * 返回成功消息
     */
    public Result<String> success(String message) {
        return Result.success(message);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected Result<String> toAjax(int rows) {
        return rows > 0 ? Result.success() : Result.error(SYSTEM_INNER_ERROR);
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected Result<String> toAjax(boolean result) {
        return result ? success() : error();
    }

}
