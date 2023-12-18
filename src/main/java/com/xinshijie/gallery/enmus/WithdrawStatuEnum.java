package com.xinshijie.gallery.enmus;

import lombok.Getter;

@Getter
public enum WithdrawStatuEnum {
    WAIT(1, "等待提现"),
    DONE(2, "提现完成"),
//    RELUFU(3, ""),
    CLOSE(4, "关闭");

    private final Integer code;
    private final String name;

    WithdrawStatuEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (WithdrawStatuEnum enable : values()) {
            if (enable.getCode() == code) {
                return enable.getName();
            }
        }
        return "";
    }
}
