package com.xinshijie.gallery.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
@TableName("user_album")
@Schema(description = " 用户创建的")
public class UserAlbum implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 创建时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 简介
     */
    private String intro;
    /**
     * 标签
     */
    private String tags;
    /**
     * /**
     * '1 免费', '2 VIP免费', '3 VIP折扣', '4 VIP独享' 5.统一
     */
    private Integer charge;


    /**
     * 价格
     */
    @Schema(description = "价格 ")
    private Double price;

    private Double vipPrice;


    /**
     * 折扣
     */
    private Double discount;
    /**
     * 模特
     */
    private String girl;
    /**
     * 查看数
     */
    private Integer countSee;
    private Integer countLike;

    /**
     * 照片数
     */
    private Integer numberPhotos;
    /**
     * 视频数
     */
    private Integer numberVideo;
    /**
     * 收藏数
     */
    private Integer countCollection;
    /**
     * 购买数
     */
    private Integer countBuy;
    private Double score;
    /**
     * 介绍
     */
    private String introduce;
    /**
     * 状态
     */
    private Integer status;

    private String imgUrl;

    private String payIntro;

}
