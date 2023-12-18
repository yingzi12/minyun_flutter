package com.xinshijie.gallery.enmus;

import lombok.Getter;

@Getter
public enum WithdrawTypeEnum {
    PAYPAY(1, "paypal"),
    BANK(2, "Bank"),

    ;

    private final Integer code;
    private final String name;

    WithdrawTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (WithdrawTypeEnum enable : values()) {
            if (enable.getCode() == code) {
                return enable.getName();
            }
        }
        return "";
    }
}
