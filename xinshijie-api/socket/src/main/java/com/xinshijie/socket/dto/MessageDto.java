package com.xinshijie.socket.dto;

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
//@Schema(description = "管理员")
public class MessageDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

//    @Schema(description = "创建时间")
    private Long createId;

    private LocalDateTime createTime;

    private Long receiveId;

    private String receiveName;

    private LocalDateTime receiveTime;

    private String receiveStatus;

//    @Schema(description = "消息内容")
    private String centent;

//    @Schema(description = "发生人")
    private String createName;

//    @Schema(description = "消息类别,1 系统信息  2用户发送信息 3回复信息")
    private Integer types;

//    @Schema(description = "是否已删")
    private Integer isDelete;

//    @Schema(description = "是否已读")
    private Integer isRead;
//    @Schema(description = "层级")
    private Integer floor;
    private Long mid;
    private Long page;

    private Long size;
}
