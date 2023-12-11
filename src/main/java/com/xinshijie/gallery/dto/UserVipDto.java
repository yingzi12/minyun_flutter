package com.xinshijie.gallery.dto;


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
@Schema(name = "UserVipDto", description = "用户vip ")
public class UserVipDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userId;

    @Size(max = 100, message = " 超出最大长度 100")
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
     * 过期时间
     */
    @Schema(description = "过期时间 ")
    private LocalDateTime expirationTime;

    /**
     * vip的用户id
     */
    @Schema(description = "vip的用户id ")
    private Long vipUserId;

    /**
     * vip的用户名称
     */
    @Schema(description = "vip的用户名称 ")
    @Size(max = 100, message = "vip的用户名称 超出最大长度 100")
    private String vipUserName;

    @Size(max = 100, message = " 超出最大长度 100")
    private String title;


    private Long pageNum;

    private Long pageSize;
}
