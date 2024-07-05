package com.xinshijie.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Size;
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
@Schema(name = "UserChatPartnerDto", description = "用户聊天对象表 ")
public class UserChatPartnerDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    @Size(max = 100, message = " 超出最大长度 100")
    private String userName;

    private Long receiverUserId;

    @Size(max = 100, message = " 超出最大长度 100")
    private String receiverUserName;

    /**
     * 创建时间
     */
    @Schema( description = "创建时间 ")
    private LocalDateTime createTime;

    /**
     * 最后聊天时间
     */
    @Schema( description = "最后聊天时间 ")
    private LocalDateTime lastTime;

private Long pageNum;

private Long pageSize;
}
