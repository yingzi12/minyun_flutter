package com.xinshijie.gallery.vo;

import com.xinshijie.gallery.domain.SystemUser;
import lombok.Data;

@Data
public class LoginUserVo {
    private String token;
    private String accessToken;
    private String refreshToken;

    private Integer code;
    private SystemUser user;

    private Long loginTime;
    private Long expireTime;

}
