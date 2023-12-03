package com.xinshijie.gallery.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
@Schema(name = "UserAttentionVo", description = "用户关注的用户 ")
public class UserAttentionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String userName;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间 ")
    private LocalDateTime createTime;

    private String attUserName;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long attUserId;


}
