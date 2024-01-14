package com.xinshijie.gallery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2023-12-06
 */
@Data
@Schema(name = "SystemUserIntroVo", description = " ")
public class SystemUserIntroVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String nickname;

    private String email;

    private Integer isEmail;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String intro;

    private String directions;
    private String imgUrl;

    private Integer countLike;
    private Integer countSee;
    private Integer countAttention;
    private Double income;
    private Double withdraw;
    private Integer isAttention;
    private Integer vip;
    private Integer credit;
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime vipExpirationTime;
    private String vipTitle;
    private String invite;
    private Integer countInvite;

}
