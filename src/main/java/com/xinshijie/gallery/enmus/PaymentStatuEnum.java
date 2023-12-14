package com.xinshijie.gallery.enmus;

import lombok.Getter;

@Getter
public enum PaymentStatuEnum {
    WAIT(1, "等待付款"),
    DONE(2, "付款完成"),
    RELUFU(3, "退款"),
    CLOSE(4, "关闭");

    private final Integer code;
    private final String name;

    PaymentStatuEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (PaymentStatuEnum enable : values()) {
            if (enable.getCode() == code) {
                return enable.getName();
            }
        }
        return "";
    }
}
