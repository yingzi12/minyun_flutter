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
 * 用户聊天记录
 * </p>
 *
 * @author 作者
 * @since 2024-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_chat_content")
@Schema(description = " 用户聊天记录")
public class UserChatContent implements Serializable{
private static final long serialVersionUID=1L;
            @Id
    @TableId(value = "id", type = IdType.AUTO)
        private Long id;
                private Long sendUserId;
                private Long receiverUserId;
    /**
     * 聊天内容
     */
                private String content;
    /**
     * 是否是图片
     */
                private Integer isImage;
    /**
     * 图片地址
     */
                private String imgUrl;
                private LocalDateTime createTime;
    /**
     * -1 待接收 0 未读 1 已读  2.撤回 4 删除
     */
                private Integer status;
}
