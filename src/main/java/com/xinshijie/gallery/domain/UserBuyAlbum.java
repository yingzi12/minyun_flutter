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
 * 用户购买记录
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_buy_album")
@Schema(description = " 用户购买记录")
public class UserBuyAlbum implements Serializable {
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
     * aid
     */
    private Long aid;
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
     * 购买时间
     */
    private LocalDateTime buyTime;
    /**
     * 状态
     */
    private Integer status;
    private String title;
}
