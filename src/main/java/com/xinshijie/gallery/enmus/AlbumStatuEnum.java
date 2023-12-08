package com.xinshijie.gallery.enmus;

import lombok.Getter;

@Getter
public enum AlbumStatuEnum {

    WAIT(1, "待发布"),
    NORMAL(2, "正常"),
    LOCK(3, "锁定"),
    DEL(4, "删除");

    private final Integer code;
    private final String name;

    AlbumStatuEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (AlbumStatuEnum enable : values()) {
            if (enable.getCode() == code) {
                return enable.getName();
            }
        }
        return "";
    }
}
