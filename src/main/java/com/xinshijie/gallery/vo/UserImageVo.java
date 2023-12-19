package com.xinshijie.gallery.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 * 用户上传的图片
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Data
@Schema(name = "UserImageVo", description = "用户上传的图片 ")
public class UserImageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String imgUrl;


    /**
     * 是否免费
     */
    @Schema(description = "是否免费 ")
    private Integer isFree;

    private LocalDateTime createTime;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long aid;

    private String md5;

    private Integer userId;


}
