package com.xinshijie.gallery.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 * 用户vip
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Data
@Schema(name = "AlbumVipVo", description = "用户vip ")
public class AlbumVipVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

        private Integer userId;

    private String userName;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间 ")
    private LocalDateTime createTime;


    /**
     * 等级
     */
    @Schema(description = "等级 ")
    private Integer rank;


    /**
     * 过期时间
     */
    @Schema(description = "过期时间 ")
    private LocalDateTime expirationTime;

    private String title;


}
