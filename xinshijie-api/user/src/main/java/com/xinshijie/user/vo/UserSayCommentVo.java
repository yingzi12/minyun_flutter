package com.xinshijie.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 * 说说回复表
 * </p>
 *
 * @author 作者
 * @since 2024-06-21
 */
@Data
@Schema(name = "UserSayCommentVo", description = "说说回复表 ")
public class UserSayCommentVo implements Serializable{

private static final long serialVersionUID=1L;


    /**
     * 评论id
     */
    @Schema(description = "评论id ")
            @JsonSerialize(using = ToStringSerializer.class)
private Long id;


    /**
     * 上级记录回复id
     */
    @Schema(description = "上级记录回复id ")
            @JsonSerialize(using = ToStringSerializer.class)
private Long upid;


    /**
     * 评论内容
     */
    @Schema(description = "评论内容 ")
        private String comment;


    /**
     * 评论回复
     */
    @Schema(description = "评论回复 ")
        private String reply;


    /**
     * 回复的层级
     */
    @Schema(description = "回复的层级 ")
        private Integer ranks;


    /**
     * 点赞数
     */
    @Schema(description = "点赞数 ")
        private Integer countLike;


    /**
     * 回复数
     */
    @Schema(description = "回复数 ")
        private Integer countReply;


    /**
     * 状态
     */
    @Schema(description = "状态 ")
        private Integer status;

            @JsonSerialize(using = ToStringSerializer.class)
private Long createId;

        private String createName;

        private LocalDateTime createTime;


    /**
     * 说说id
     */
    @Schema(description = "说说id ")
            @JsonSerialize(using = ToStringSerializer.class)
private Long usid;


    /**
     * 层级为1回复的id
     */
    @Schema(description = "层级为1回复的id ")
            @JsonSerialize(using = ToStringSerializer.class)
private Long pid;

        private String circleUrl;

        private String nickname;


    /**
     * 被回复人id
     */
    @Schema(description = "被回复人id ")
            @JsonSerialize(using = ToStringSerializer.class)
private Long replyUserId;


    /**
     * 被回复人姓名
     */
    @Schema(description = "被回复人姓名 ")
        private String replyUserName;


    /**
     * 被回复人昵称
     */
    @Schema(description = "被回复人昵称 ")
        private String replyUserNickname;


}
