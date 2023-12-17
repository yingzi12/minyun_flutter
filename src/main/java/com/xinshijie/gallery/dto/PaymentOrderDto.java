package com.xinshijie.gallery.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 作者
 * @since 2023-12-13
 */
@Data
@Schema(name = "PaymentOrderDto", description = " ")
public class PaymentOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 金额
     */
    @Schema(description = "金额 ")
    private Double amount;

    @Size(max = 20, message = " 超出最大长度 20")
    private String payId;

    /**
     * 物品名称
     */
    @Schema(description = "物品名称 ")
    @Size(max = 100, message = "物品名称 超出最大长度 100")
    private String productName;

    /**
     * 订单说明
     */
    @Schema(description = "订单说明 ")
    @Size(max = 100, message = "订单说明 超出最大长度 100")
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

    @Size(max = 100, message = " 超出最大长度 100")
    private String username;

    /**
     * 货币
     */
    @Schema(description = "货币 ")
    @Size(max = 30, message = "货币 超出最大长度 30")
    private String country;

    /**
     * 物品类别 1 网站会员，2 用户会员 3网站消费 4.用户图集
     */
    @Schema(description = "物品类别 1 网站会员，2 用户会员 3网站消费 4.用户图集 ")
    private Integer kind;

    private List<Integer> kinds;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式 ")
    @Size(max = 100, message = "支付方式 超出最大长度 100")
    private String payType;

    /**
     * 物品类别
     */
    private Integer productId;


    private Integer  incomeUserId;

    private LocalDateTime expiredTime;

    private String requestId;
    private Long pageNum;

    private Long pageSize;
}
