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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String intro;

    private String directions;
    private String imgUrl;

    private Integer countLike;
    private Integer countSee;
    private Integer countAttention;
    private Double income;
    private Double withdraw;


}
