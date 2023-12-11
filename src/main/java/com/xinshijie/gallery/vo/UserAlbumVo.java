package com.xinshijie.gallery.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.xinshijie.gallery.domain.UserImage;
import com.xinshijie.gallery.domain.UserVideo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * <p>
 * 用户创建的
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Data
@Schema(name = "UserAlbumVo", description = "用户创建的 ")
public class UserAlbumVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 标题
     */
    @Schema(description = "标题 ")
    private String title;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间 ")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间 ")
    private LocalDateTime updateTime;

    /**
     * 用户id
     */
    @Schema(description = "用户id ")
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer userId;

    /**
     * 用户名称
     */
    @Schema(description = "用户名称 ")
    private String userName;

    /**
     * 简介
     */
    @Schema(description = "简介 ")
    private String intro;

    /**
     * 标签
     */
    @Schema(description = "标签 ")
    private String tags;

    /**
     * 折扣
     */
    @Schema(description = "折扣 ")
    private Double discount;

    /**
     * 模特
     */
    @Schema(description = "模特 ")
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
    private String introduce;

    /**
     * 状态
     */
    @Schema(description = "状态 ")
    private Integer status;

    private Integer charge;

    @Schema(description = "价格 ")
    private Double price;

    @Schema(description = " vip 价格 ")
    private Double vipPrice;

    private Boolean isSee;

    private Integer isVip;

    private List<UserImage> imageList;

    private List<UserVideo> videoList;

    private Integer imageCount;
    private Integer videoCount;

}
