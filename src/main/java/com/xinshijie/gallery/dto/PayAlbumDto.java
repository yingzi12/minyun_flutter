package com.xinshijie.gallery.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PayAlbumDto {
    @NotNull
    private Double amount;

    /**
     * 物品类别 1 网站会员，2 用户会员 3网站消费 4.用户图集
     */
    @NotNull
    private Integer kind;

    /**
     * 物品类别  kind 是1 会员编号  ,2 用户会员id, 4 会员图集id
     *
     */
    @NotNull
    private Integer productId;

    @Size(max = 100,min = 5)
    @NotNull
    private String productName;

    @Size(max = 300,min = 5,message = "description 超出大小")
    private String description;

    private Integer incomeUserId;

}
