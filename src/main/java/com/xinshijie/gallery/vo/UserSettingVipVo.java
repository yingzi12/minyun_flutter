package com.xinshijie.gallery.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 * 用户vip
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Data
@Schema(name = "UserSettingVipVo", description = "用户vip ")
public class UserSettingVipVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userId;

    private String userName;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间 ")
    private LocalDateTime createTime;


    /**
     * 等级
     */
    @Schema(description = "等级 ")
    private Integer rank;


    /**
     * 更新时间
     */
    @Schema(description = "更新时间 ")
    private LocalDateTime updateTime;


    /**
     * 价格
     */
    @Schema(description = "价格 ")
    private Double price;


    /**
     * 简介
     */
    @Schema(description = "简介 ")
    private String intro;


    /**
     * 时间长度
     */
    @Schema(description = "时间长度 ")
    private Integer timeLong;


    /**
     * 时间类型  1天 2月 3年 4永
     */
    @Schema(description = "时间类型  1天 2月 3年 4永 ")
    private Integer timeType;


    /**
     * 购买人数
     */
    @Schema(description = "购买人数 ")
    private Integer countBuy;


    /**
     * 详细
     */
    @Schema(description = "详细 ")
    private String introduce;


    /**
     * 标题
     */
    @Schema(description = "标题 ")
    private String title;


}
