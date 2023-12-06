package com.xinshijie.gallery.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserPasswordDto {
    @NotNull(message = "旧密码不能为空")
    private String oldPassword;
    @NotNull(message = "新密码不能为空")
    private String newPassword;
    private String key;
    private String check;
}

