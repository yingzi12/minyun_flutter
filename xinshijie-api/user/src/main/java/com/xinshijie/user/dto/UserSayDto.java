package com.xinshijie.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户说说，
 * </p>
 *
 * @author 作者
 * @since 2024-06-21
 */
@Data
@Schema(name = "UserSayDto", description = "用户说说， ")
public class UserSayDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    @Size(max = 100, message = " 超出最大长度 100")
    private String userName;

    @Size(max = 100, message = " 超出最大长度 100")
    private String userNickname;

    private LocalDateTime createTime;

    /**
     * 内容
     */
    @Schema( description = "内容 ")
    @Size(max = 500, message = "内容 超出最大长度 500")
    private String content;

    /**
     * 图片列表
     */
    @Schema( description = "图片列表 ")
    @Size(max = 500, message = "图片列表 超出最大长度 500")
    private String urls;

    private LocalDateTime updateTime;

    /**
     * 点赞
     */
    @Schema( description = "点赞 ")
    private Integer countLike;

private Long pageNum;

private Long pageSize;
}
