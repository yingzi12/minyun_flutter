package com.xinshijie.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 说说回复表
 * </p>
 *
 * @author 作者
 * @since 2024-06-18
 */
@Data
@Schema(name = "UserSayCommentDto", description = "说说回复表 ")
public class UserSayCommentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @Schema( description = "评论id ")
    private Long id;

    /**
     * 上级记录回复id
     */
    @Schema( description = "上级记录回复id ")
    private Long upid;

    /**
     * 评论内容
     */
    @Schema( description = "评论内容 ")
    @Size(max = 500, message = "评论内容 超出最大长度 500")
    private String comment;

    /**
     * 评论回复
     */
    @Schema( description = "评论回复 ")
    @Size(max = 500, message = "评论回复 超出最大长度 500")
    private String reply;

    /**
     * 回复的层级
     */
    @Schema( description = "回复的层级 ")
    private Integer ranks;

    /**
     * 点赞数
     */
    @Schema( description = "点赞数 ")
    private Integer countLike;

    /**
     * 回复数
     */
    @Schema( description = "回复数 ")
    private Integer countReply;

    /**
     * 状态
     */
    @Schema( description = "状态 ")
    private Integer status;

    private Long createId;

    @Size(max = 50, message = " 超出最大长度 50")
    private String createName;

    private LocalDateTime createTime;

    /**
     * 说说id
     */
    @Schema( description = "说说id ")
    private Long usid;

    /**
     * 层级为1回复的id
     */
    @Schema( description = "层级为1回复的id ")
    private Long pid;

    @Size(max = 100, message = " 超出最大长度 100")
    private String circleUrl;

    @Size(max = 100, message = " 超出最大长度 100")
    private String nickname;

private Long pageNum;

private Long pageSize;
}
