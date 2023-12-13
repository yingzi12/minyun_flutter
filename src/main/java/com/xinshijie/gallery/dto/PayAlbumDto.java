package com.xinshijie.gallery.dto;

import lombok.Data;

@Data
public class PayAlbumDto {
    private Integer aid;

    private Integer vid;


    private Double amount;

    /**
     * 物品类别 1 网站会员，2 用户会员 3网站消费 4.用户图集
     */
    private Integer kind;

    /**
     * 物品类别 1 ,3,4,5
     */
    private Integer productType;

}
