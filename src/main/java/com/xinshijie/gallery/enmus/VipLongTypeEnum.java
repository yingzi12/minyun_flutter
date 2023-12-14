package com.xinshijie.gallery.enmus;

import lombok.Getter;

@Getter
public enum VipLongTypeEnum {

    DAY(1,"太"),
    WEEK(2,"周"),
    MONTH(3,"月"),
    YEAR(4,"年"),
    FOREVER(5,"永久");

    private final Integer code;
    private final String name;

    VipLongTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (VipLongTypeEnum enable : values()) {
            if (enable.getCode() == code) {
                return enable.getName();
            }
        }
        return "";
    }
}
