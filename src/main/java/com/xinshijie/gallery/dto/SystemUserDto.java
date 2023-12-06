package com.xinshijie.gallery.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import jakarta.validation.constraints.Size;
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
public class SystemUserDto implements Serializable {

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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @Size(max = 100, message = " 超出最大长度 100")
    private String intro;

    @Size(max = 100, message = " 超出最大长度 100")
    private String directions;

private Long pageNum;

private Long pageSize;
}
