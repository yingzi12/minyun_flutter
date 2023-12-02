package com.xinshijie.gallery.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName album
 */
@TableName(value = "album")
@Data
public class AlbumDto implements Serializable {
    private Long id;
    private String title;
    private Integer pageSize;
    private Integer pageNum;
    private String order;

}