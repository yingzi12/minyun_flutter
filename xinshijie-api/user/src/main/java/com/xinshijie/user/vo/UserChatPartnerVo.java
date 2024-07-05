package com.xinshijie.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
@Schema(name = "UserChatPartnerVo", description = "用户聊天对象表 ")
public class UserChatPartnerVo implements Serializable{

private static final long serialVersionUID=1L;

            @JsonSerialize(using = ToStringSerializer.class)
private Long id;

            @JsonSerialize(using = ToStringSerializer.class)
private Long userId;

        private String userName;

            @JsonSerialize(using = ToStringSerializer.class)
private Long receiverUserId;

        private String receiverUserName;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间 ")
        private LocalDateTime createTime;


    /**
     * 最后聊天时间
     */
    @Schema(description = "最后聊天时间 ")
        private LocalDateTime lastTime;


}
