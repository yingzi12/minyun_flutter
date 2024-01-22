package com.xinshijie.gallery.dto;

import lombok.Data;

@Data
public class ImageDto {
    private Integer aid;

    private Integer pageSize;

    private Integer pageNum;

    private Integer offset;
    private String imgUrl;
    private String md5;

}
