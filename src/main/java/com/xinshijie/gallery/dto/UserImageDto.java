package com.xinshijie.gallery.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
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
@Schema(name = "UserImageDto", description = "用户上传的图片 ")
public class UserImageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(max = 100, message = " 超出最大长度 100")
    private String url;


    /**
     * 是否免费
     */
    @Schema(description = "是否免费 ")
    private Integer isFree;

    private LocalDateTime createTime;

    private Integer aid;

    private Long pageNum;

    private Long pageSize;


    private String md5;

    private Integer userId;

}
