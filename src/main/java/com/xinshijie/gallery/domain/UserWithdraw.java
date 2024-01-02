package com.xinshijie.gallery.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.io.Serial;
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
@EqualsAndHashCode(callSuper = false)
@TableName("user_withdraw")
@Schema(description = " 用户提现记录")
public class UserWithdraw implements Serializable{
    private static final long serialVersionUID=1L;
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String userName;
    /**
     * 创建时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 提现时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /**
     * 提现金额
     */
    private Double amount;

    private Double amountReceived;

    /**
     * 状态 1.待提现  2提现完成 3提现失败
     */
    private Integer status;
    /**
     * email
     */
    private String email;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 提现名称
     */
    private String withdrawName;
    /**
     * 说明
     */
    private String explanation;
    /**
     * 提现类别 1.paypal 2.银行转
     */
    private Integer withdrawType;
}
