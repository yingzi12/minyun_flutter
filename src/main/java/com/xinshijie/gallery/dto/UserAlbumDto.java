package com.xinshijie.gallery.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户创建的
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Data
@Schema(name = "UserAlbumDto", description = "用户创建的 ")
public class UserAlbumDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 标题
     */
    @Schema(description = "标题 ")
    @Size(max = 100, message = "标题 超出最大长度 100")
    private String title;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间 ")
    private LocalDate createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间 ")
    private LocalDateTime updateTime;

    /**
     * 用户id
     */
    @Schema(description = "用户id ")
    private Long userId;

    /**
     * 用户名称
     */
    @Schema(description = "用户名称 ")
    @Size(max = 100, message = "用户名称 超出最大长度 100")
    private String userName;

    /**
     * 简介
     */
    @Schema(description = "简介 ")
    @Size(max = 100, message = "简介 超出最大长度 100")
    private String intro;

    /**
     * 标签
     */
    @Schema(description = "标签 ")
    @Size(max = 100, message = "标签 超出最大长度 100")
    private String tags;

    /**
     * 是否vip
     */
    @Schema(description = "是否vip ")
    private Integer isVip;

    /**
     * 是否免费
     */
    @Schema(description = "是否免费 ")
    private Integer isFree;

    /**
     * 价格
     */
    @Schema(description = "价格 ")
    private Double amount;

    /**
     * 折扣
     */
    @Schema(description = "折扣 ")
    private Double discount;

    /**
     * 模特
     */
    @Schema(description = "模特 ")
    @Size(max = 100, message = "模特 超出最大长度 100")
    private String gril;

    /**
     * 查看数
     */
    @Schema(description = "查看数 ")
    private Integer countSee;

    /**
     * 照片数
     */
    @Schema(description = "照片数 ")
    private Integer numberPhotos;

    /**
     * 视频数
     */
    @Schema(description = "视频数 ")
    private Integer numberVideo;

    /**
     * 收藏数
     */
    @Schema(description = "收藏数 ")
    private Integer countCollection;

    /**
     * 购买数
     */
    @Schema(description = "购买数 ")
    private Integer countBuy;

    private Double score;

    /**
     * 介绍
     */
    @Schema(description = "介绍 ")
    @Size(max = 500, message = "介绍 超出最大长度 500")
    private String introduce;

    /**
     * 状态
     */
    @Schema(description = "状态 ")
    private Integer status;

    private Long pageNum;

    private Long pageSize;
}
