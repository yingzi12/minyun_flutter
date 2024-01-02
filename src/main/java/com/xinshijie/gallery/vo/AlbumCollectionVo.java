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
 * 用户收藏的album
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Data
@Schema(name = "AlbumCollectionVo", description = "用户收藏的album ")
public class AlbumCollectionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

        private Integer userId;

    private String userName;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间 ")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private String title;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long aid;


}
