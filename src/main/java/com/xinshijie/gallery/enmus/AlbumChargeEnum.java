package com.xinshijie.gallery.enmus;

import lombok.Getter;

@Getter
public enum AlbumChargeEnum {

    //'1 免费', '2 VIP免费', '3 VIP折扣', '4 VIP独享' 5.统一
    FREE(1, "免费"),
    VIP_FREE(2, "VIP免费"),
    VIP_DISCOUNT(3, "VIP折扣"),
    VIP_EXCLUSIVE(4, "VIP独享"),
    UNIFICATION(5, "统一");

    private final Integer code;
    private final String name;

    AlbumChargeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (AlbumChargeEnum enable : values()) {
            if (enable.getCode() == code) {
                return enable.getName();
            }
        }
        return "";
    }
}
