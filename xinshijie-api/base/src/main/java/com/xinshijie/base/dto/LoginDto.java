package com.xinshijie.base.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
    private String uuid;
    private String code;

}
