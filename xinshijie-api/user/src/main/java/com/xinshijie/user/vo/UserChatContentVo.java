package com.xinshijie.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
@Schema(name = "UserChatContentVo", description = "用户聊天记录 ")
public class UserChatContentVo implements Serializable{

private static final long serialVersionUID=1L;

            @JsonSerialize(using = ToStringSerializer.class)
private Long id;

            @JsonSerialize(using = ToStringSerializer.class)
private Long sendUserId;

            @JsonSerialize(using = ToStringSerializer.class)
private Long receiverUserId;


    /**
     * 聊天内容
     */
    @Schema(description = "聊天内容 ")
        private String content;


    /**
     * 是否是图片
     */
    @Schema(description = "是否是图片 ")
        private Integer isImage;


    /**
     * 图片地址
     */
    @Schema(description = "图片地址 ")
        private String imgUrl;

        private LocalDateTime createTime;


    /**
     * -1 待接收 0 未读 1 已读  2.撤回 4 删除
     */
    @Schema(description = "-1 待接收 0 未读 1 已读  2.撤回 4 删除 ")
        private Integer status;


}
