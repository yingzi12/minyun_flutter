package com.xinshijie.gallery.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 用户vip
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_setting_vip")
@Schema(description = " 用户vip")
public class UserSettingVip implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String userName;
    /**
     * 创建时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 等级
     */
    @TableField("`rank`")
    private Integer rank;
    /**
     * 更新时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /**
     * 价格
     */
    private Double price;
    /**
     * 简介
     */
    private String intro;
    /**
     * 时间长度
     */
    private Integer timeLong;
    /**
     * 时间类型  1天 2月 3年 4永
     */
    private Integer timeType;
    /**
     * 购买人数
     */
    private Integer countBuy;
    /**
     * 详细
     */
    private String introduce;
    /**
     * 标题
     */
    private String title;

    private Integer status;
}
