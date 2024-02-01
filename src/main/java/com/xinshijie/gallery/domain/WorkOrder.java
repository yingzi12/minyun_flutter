package com.xinshijie.gallery.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.io.Serial;
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
@EqualsAndHashCode(callSuper = false)
@TableName("work_order")
@Schema(description = " 服务工单")
public class WorkOrder implements Serializable{
    private static final long serialVersionUID=1L;
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    /**
     * 说明
     */
    private String explanation;
    private String imgUrls;
    /**
     * 2 待处理  1已处理 3拒绝
     */
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
    private String reply;
}
