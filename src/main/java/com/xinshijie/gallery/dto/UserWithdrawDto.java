package com.xinshijie.gallery.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户提现记录
 * </p>
 *
 * @author 作者
 * @since 2023-12-18
 */
@Data
@Schema(name = "UserWithdrawDto", description = "用户提现记录 ")
public class UserWithdrawDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userId;

    @Size(max = 100, message = " 超出最大长度 100")
    private String userName;

    /**
     * 创建时间
     */
    @Schema( description = "创建时间 ")
    private LocalDateTime createTime;

    /**
     * 提现时间
     */
    @Schema( description = "提现时间 ")
    private LocalDateTime updateTime;

    /**
     * 提现金额
     */
    @Schema( description = "提现金额 ")
    private Double amount;

    private Double amountReceived;

    /**
     * 状态 1.待提现  2提现完成 3提现失败
     */
    @Schema( description = "状态 1.待提现  2提现完成 3提现失败 ")
    private Integer status;

    /**
     * email
     */
    @Schema( description = "email ")
    @Size(max = 100, message = "email 超出最大长度 100")
    private String email;

    /**
     * 银行名称
     */
    @Schema( description = "银行名称 ")
    @Size(max = 100, message = "银行名称 超出最大长度 100")
    private String bankName;

    /**
     * 提现名称
     */
    @Schema( description = "提现名称 ")
    @Size(max = 100, message = "提现名称 超出最大长度 100")
    private String withdrawName;

    /**
     * 说明
     */
    @Schema( description = "说明 ")
    @Size(max = 100, message = "说明 超出最大长度 100")
    private String explanation;

    /**
     * 提现类别 1.paypal 2.银行转
     */
    @Schema( description = "提现类别 1.paypal 2.银行转 ")
    private Integer withdrawType;

private Long pageNum;

private Long pageSize;
}
