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
 * 用户聊天对象表
 * </p>
 *
 * @author 作者
 * @since 2024-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_chat_partner")
@Schema(description = " 用户聊天对象表")
public class UserChatPartner implements Serializable{
private static final long serialVersionUID=1L;
            @Id
    @TableId(value = "id", type = IdType.AUTO)
        private Long id;
                private Long userId;
                private String userName;
                private Long receiverUserId;
                private String receiverUserName;
    /**
     * 创建时间
     */
                private LocalDateTime createTime;
    /**
     * 最后聊天时间
     */
                private LocalDateTime lastTime;
}
