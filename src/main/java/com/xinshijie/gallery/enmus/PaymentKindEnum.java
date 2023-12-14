package com.xinshijie.gallery.enmus;

import lombok.Getter;

@Getter
public enum PaymentKindEnum {
    SYSTEM_MEMBER(1, "系统会员"),
    USER_MEMBER(2, "用户会员"),
    SYSTEM_OTHER(3, "系统其他"),
    USER_ALBUM(4, "图集");

    private final Integer code;
    private final String name;

    PaymentKindEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (PaymentKindEnum enable : values()) {
            if (enable.getCode() == code) {
                return enable.getName();
            }
        }
        return "";
    }
}
