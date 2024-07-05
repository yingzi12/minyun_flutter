package com.xinshijie.socket.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 管理员
 * </p>
 *
 * @author zx
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wiki_message")
@Schema(description = "管理员")
public class TestMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "发送人")
    private Long createId;

    @Schema(description = "发送时间")
    private LocalDateTime createTime;

    @Schema(description = "接收人id")
    private Long receiveId;

    @Schema(description = "接收人姓名")
    private String receiveName;

    @Schema(description = "接收人时间")
    private LocalDateTime receiveTime;

    @Schema(description = "接收人状态")
    private String receiveStatus;

    @Schema(description = "消息内容")
    private String centent;

    @Schema(description = "发生人")
    private String createName;

    @Schema(description = "消息类别,1 系统信息  2用户发送信息 3回复信息")
    private Integer types;

    @Schema(description = "是否已删")
    private Integer isDelete;

    @Schema(description = "是否已读")
    private Integer isRead;

    @Schema(description = "层级")
    private Integer floor;

    private Long mid;
}
