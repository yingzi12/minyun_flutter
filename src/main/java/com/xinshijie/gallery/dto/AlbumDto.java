package com.xinshijie.gallery.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @TableName album
 */
@Data
public class AlbumDto implements Serializable {
    private Integer id;
    private String title;
    private Integer isFree;
    private Integer pageSize;
    private Integer offset;
    private Integer pageNum;
    private String order;
    private String notSource;
}