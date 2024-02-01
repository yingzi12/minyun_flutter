package com.xinshijie.gallery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
@Schema(name = "WorkOrderVo", description = "服务工单 ")
public class WorkOrderVo implements Serializable{

    private static final long serialVersionUID=1L;

    private Integer id;

    private String title;


    /**
     * 说明
     */
    @Schema(description = "说明 ")
    private String explanation;

    private String imgUrls;


    /**
     * 2 待处理  1已处理 3拒绝
     */
    @Schema(description = "2 待处理  1已处理 3拒绝 ")
    private Integer status;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Integer userId;

    private String userName;


    /**
     * 回复
     */
    @Schema(description = "回复 ")
    private String reply;


}
