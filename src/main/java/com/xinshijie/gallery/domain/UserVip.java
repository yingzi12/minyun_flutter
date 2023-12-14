package com.xinshijie.gallery.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户vip
 * </p>
 *
 * @author 作者
 * @since 2023-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_vip")
@Schema(description = " 用户vip")
public class UserVip implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String userName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 等级
     */
    private Integer rank;
    /**
     * 过期时间
     */
    private LocalDateTime expirationTime;
    private LocalDateTime updateTime;

    /**
     * vip的用户id
     */
    private Integer vipUserId;
    /**
     * vip的用户名称
     */
    private String vipUserName;
    private String title;
    private Integer vid;

}
