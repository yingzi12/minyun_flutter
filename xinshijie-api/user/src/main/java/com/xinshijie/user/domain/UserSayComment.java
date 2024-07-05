package com.xinshijie.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.io.Serial;
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
@EqualsAndHashCode(callSuper = false)
@TableName("wiki_user_say_comment")
@Schema(description = " 说说回复表")
public class UserSayComment implements Serializable{
private static final long serialVersionUID=1L;
    /**
     * 评论id
     */
            @Id
    @TableId(value = "id", type = IdType.AUTO)
        private Long id;
    /**
     * 上级记录回复id
     */
                private Long upid;
    /**
     * 评论内容
     */
                private String comment;
    /**
     * 评论回复
     */
                private String reply;
    /**
     * 回复的层级
     */
                private Integer ranks;
    /**
     * 点赞数
     */
                private Integer countLike;
    /**
     * 回复数
     */
                private Integer countReply;
    /**
     * 状态
     */
                private Integer status;
                private Long createId;
                private String createName;
                private LocalDateTime createTime;
    /**
     * 说说id
     */
                private Long usid;
    /**
     * 层级为1回复的id
     */
                private Long pid;
                private String circleUrl;
                private String nickname;
    /**
     * 被回复人id
     */
                private Long replyUserId;
    /**
     * 被回复人姓名
     */
                private String replyUserName;
    /**
     * 被回复人昵称
     */
                private String replyUserNickname;
}
