package com.xinshijie.gallery.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 *
 * </p>
 *
 * @author 作者
 * @since 2023-12-13
 */
@Data
@Schema(name = "PaymentOrderVo", description = " ")
public class PaymentOrderVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;


    /**
     * 金额
     */
    @Schema(description = "金额 ")
    private Double amount;

    private String payId;


    /**
     * 物品名称
     */
    @Schema(description = "物品名称 ")
    private String productName;


    /**
     * 订单说明
     */
    @Schema(description = "订单说明 ")
    private String description;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间 ")
    private LocalDateTime createTime;


    /**
     * 支付时间
     */
    @Schema(description = "支付时间 ")
    private LocalDateTime payTime;


    /**
     * 状态 1 等待支付，2支付完成 3退款 4取消
     */
    @Schema(description = "状态 1 等待支付，2支付完成 3退款 4取消 ")
    private Integer status;


    /**
     * 用户id
     */
    @Schema(description = "用户id ")
    private Integer userId;

    private String username;


    /**
     * 货币
     */
    @Schema(description = "货币 ")
    private String country;


    /**
     * 物品类别 1 网站会员，2 用户会员 3网站消费 4.用户图集
     */
    @Schema(description = "物品类别 1 网站会员，2 用户会员 3网站消费 4.用户图集 ")
    private Integer kind;


    /**
     * 物品类别
     */
    @Schema(description = "物品类别 ")
    private Integer productType;


    /**
     * 支付方式
     */
    @Schema(description = "支付方式 ")
    private String payType;


}
