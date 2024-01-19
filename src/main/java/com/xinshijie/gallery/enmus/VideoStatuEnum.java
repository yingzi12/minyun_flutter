package com.xinshijie.gallery.enmus;

import lombok.Getter;

@Getter
public enum VideoStatuEnum {

    WAIT(1, "等待"),
    NORMAL(2, "转码中"),
    LOCK(3, "转码成功"),
    DEL(4, "删除");

    private final Integer code;
    private final String name;

    VideoStatuEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (VideoStatuEnum enable : values()) {
            if (enable.getCode() == code) {
                return enable.getName();
            }
        }
        return "";
    }
}
