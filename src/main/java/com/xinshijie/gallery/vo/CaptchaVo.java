package com.xinshijie.gallery.vo;

import lombok.Data;

@Data
public class CaptchaVo {
    private String uuid;
    private String img;
    private Boolean captchaEnabled;
    private Integer code = 200;
}