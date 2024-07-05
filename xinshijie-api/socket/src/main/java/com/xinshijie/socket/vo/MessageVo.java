package com.xinshijie.socket.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@Schema(description = "管理员")
public class MessageVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(description = "创建人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createId;

    private LocalDateTime createTime;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long receiveId;

    private String receiveName;

    private LocalDateTime receiveTime;

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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long mid;

    private Long page;

    private Long size;
}
