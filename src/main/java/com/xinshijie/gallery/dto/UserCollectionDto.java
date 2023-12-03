package com.xinshijie.gallery.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户收藏的album
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Data
@Schema(name = "UserCollectionDto", description = "用户收藏的album ")
public class UserCollectionDto implements Serializable {

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

    @Size(max = 100, message = " 超出最大长度 100")
    private String title;

    private Long aid;

    private Long pageNum;

    private Long pageSize;
}
