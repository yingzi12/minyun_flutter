package com.xinshijie.gallery.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
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
@Schema(name = "UserBuyVipDto", description = "用户购买记录 ")
public class UserBuyVipDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Long userId;

    @Size(max = 100, message = " 超出最大长度 100")
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

    @Size(max = 100, message = " 超出最大长度 100")
    private String title;

    private Long pageNum;

    private Long pageSize;
}
