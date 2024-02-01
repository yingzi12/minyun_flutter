package com.xinshijie.gallery.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务工单
 * </p>
 *
 * @author 作者
 * @since 2024-02-01
 */
@Data
@Schema(name = "WorkOrderDto", description = "服务工单 ")
public class WorkOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @Size(max = 100, message = " 超出最大长度 100")
    private String title;

    /**
     * 说明
     */
    @Schema( description = "说明 ")
    @Size(max = 500, message = "说明 超出最大长度 500")
    private String explanation;

    private String imgUrls;

    /**
     * 2 待处理  1已处理 3拒绝
     */
    @Schema( description = "2 待处理  1已处理 3拒绝 ")
    private Integer status;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    private Integer userId;

    @Size(max = 100, message = " 超出最大长度 100")
    private String userName;

    /**
     * 回复
     */
    @Schema( description = "回复 ")
    @Size(max = 100, message = "回复 超出最大长度 100")
    private String reply;

private Long pageNum;

private Long pageSize;
}
