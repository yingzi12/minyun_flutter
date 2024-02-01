package com.xinshijie.gallery.enmus;

import lombok.Getter;

@Getter
public enum WorkStatuEnum {
    WAIT(1, "wait","等待"),
    PROCESSED(2, "processed","已处理"),
    REJECT(3, "reject","拒绝"),

    ;

    private final Integer code;
    private final String name;

    private final String desc;


    WorkStatuEnum(Integer code, String name,String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public static String getNameByCode(Integer code) {
        for (WorkStatuEnum enable : values()) {
            if (enable.getCode() == code) {
                return enable.getName();
            }
        }
        return "";
    }
}
