package org.xinshijie.gallery.dto;

import lombok.Data;

@Data
public class ImageDto {
    private Long aid;

    private Integer pageSize;
    private Integer pageNum;
}
