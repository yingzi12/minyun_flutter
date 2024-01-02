package com.xinshijie.gallery.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户关注的用户
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_attention")
@Schema(description = " 用户关注的用户")
public class UserAttention implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String userName;
    /**
     * 创建时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String attUserName;
    private Integer attUserId;
}
