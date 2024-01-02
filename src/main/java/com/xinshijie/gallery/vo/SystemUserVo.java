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
@Schema(name = "SystemUserVo", description = " ")
public class SystemUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String nickname;

    private String email;

    private Integer isEmail;

    private String password;

    private String salt;

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
    private Double amountReceived;


}
