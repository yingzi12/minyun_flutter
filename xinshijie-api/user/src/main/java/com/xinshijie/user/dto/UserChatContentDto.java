package com.xinshijie.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Size;
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
@Schema(name = "UserChatContentDto", description = "用户聊天记录 ")
public class UserChatContentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long sendUserId;

    private Long receiverUserId;

    /**
     * 聊天内容
     */
    @Schema( description = "聊天内容 ")
    @Size(max = 500, message = "聊天内容 超出最大长度 500")
    private String content;

    /**
     * 是否是图片
     */
    @Schema( description = "是否是图片 ")
    private Integer isImage;

    /**
     * 图片地址
     */
    @Schema( description = "图片地址 ")
    @Size(max = 100, message = "图片地址 超出最大长度 100")
    private String imgUrl;

    private LocalDateTime createTime;

    /**
     * -1 待接收 0 未读 1 已读  2.撤回 4 删除
     */
    @Schema( description = "-1 待接收 0 未读 1 已读  2.撤回 4 删除 ")
    private Integer status;

private Long pageNum;

private Long pageSize;
}
