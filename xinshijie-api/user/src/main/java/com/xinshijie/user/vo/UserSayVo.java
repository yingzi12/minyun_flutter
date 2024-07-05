package com.xinshijie.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
@Schema(name = "UserSayVo", description = "用户说说， ")
public class UserSayVo implements Serializable{

    private static final long serialVersionUID=1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String userName;

    private String userNickname;

    private LocalDateTime createTime;

    /**
     * 内容
     */
    @Schema(description = "内容 ")
    private String content;


    /**
     * 图片列表
     */
    @Schema(description = "图片列表 ")
    private String urls;

    private LocalDateTime updateTime;

    /**
     * 点赞
     */
    @Schema(description = "点赞 ")
    private Integer countLike;

    private String avatar;

}
