package com.xinshijie.gallery.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 作者
 * @since 2023-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("system_user")
@Schema(description = " ")
public class SystemUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String nickname;
    private String email;
    private Integer isEmail;
    private String password;
    private String salt;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String intro;
    private String directions;
    private String imgUrl;
    private Integer countLike;
    private Integer countSee;
    private Integer countAttention;
    private Integer vip;
    private Integer credit;
    private Double income;
    private Double withdraw;
    private LocalDateTime vipExpirationTime;

}
