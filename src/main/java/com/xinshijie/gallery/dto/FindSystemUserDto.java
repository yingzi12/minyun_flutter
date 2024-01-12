package com.xinshijie.gallery.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Schema(name = "SystemUserDto", description = " ")
public class FindSystemUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @Size(max = 100, message = " 超出最大长度 100")
    @NotNull
    private String name;

    @Size(max = 100, message = " 超出最大长度 100")
    private String nickname;

    @Size(max = 100, message = " 超出最大长度 100")
    @Email
    private String email;

    private Integer isEmail;

    @Size(max = 100, message = " 超出最大长度 100")
    private String password;

    @Size(max = 20, message = " 超出最大长度 20")
    private String salt;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Size(max = 100, message = " 超出最大长度 100")
    private String intro;

    @Size(max = 100, message = " 超出最大长度 100")
    private String directions;

    private Integer countLike;
    private Integer countSee;
    private Integer countAttention;
    private String orderBy;

    private Long pageNum;

    private Long pageSize;
}
