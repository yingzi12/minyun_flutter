package com.xinshijie.gallery.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
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
@Schema(name = "UserSettingVipDto", description = "用户vip ")
public class UserSettingVipDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userId;

    @Size(max = 100, message = " 超出最大长度 100")
    private String userName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间 ")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
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
    @Size(max = 300, message = "简介 超出最大长度 300")
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
    @Size(max = 500, message = "详细 超出最大长度 500")
    private String introduce;

    /**
     * 标题
     */
    @Schema(description = "标题 ")
    @Size(max = 100, message = "标题 超出最大长度 100")
    private String title;

    private Integer status;


    private Long pageNum;

    private Long pageSize;
}
