package com.xinshijie.gallery.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 用户上传的图片
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("all_video")
@Schema(description = " 用户上传的图片")
public class AllVideo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String sourceWeb;
    private String sourceUrl;
    private String md5;
    private Long size;
    private String title;
    /**
     * 缓存地址
     */
    private String url;
    private Integer status;
    /**
     * 视频时长 ,单位：毫秒
     */
    private Long duration;

    private String imgUrl;
}
