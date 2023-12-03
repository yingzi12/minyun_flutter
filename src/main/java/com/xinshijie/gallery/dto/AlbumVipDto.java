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
@Schema(name = "AlbumVipDto", description = "用户vip ")
public class AlbumVipDto implements Serializable {

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
     * 等级
     */
    @Schema(description = "等级 ")
    private Integer rank;

    /**
     * 过期时间
     */
    @Schema(description = "过期时间 ")
    private LocalDateTime expirationTime;

    @Size(max = 100, message = " 超出最大长度 100")
    private String title;

    private Long pageNum;

    private Long pageSize;
}
