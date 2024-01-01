package com.xinshijie.gallery.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
@Schema(name = "UserBuyVipVo", description = "用户购买记录 ")
public class UserBuyVipVo implements Serializable {

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
     * aid
     */
    @Schema(description = "aid ")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long vid;


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
     * 购买时间
     */
    @Schema(description = "购买时间 ")
    private LocalDateTime buyTime;


    /**
     * 状态
     */
    @Schema(description = "状态 ")
    private Integer status;

    private String title;


}
