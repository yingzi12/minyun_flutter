package com.xinshijie.gallery.enmus;

import lombok.Getter;

@Getter
public enum VipStatuEnum {

    NORMAL(1, "正常"),
    WAIT(2, "待发布"),
    DEL(4, "删除");

    private final Integer code;
    private final String name;

    VipStatuEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (VipStatuEnum enable : values()) {
            if (enable.getCode() == code) {
                return enable.getName();
            }
        }
        return "";
    }
}
