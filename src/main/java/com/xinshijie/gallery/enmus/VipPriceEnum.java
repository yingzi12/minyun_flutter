package com.xinshijie.gallery.enmus;

import lombok.Data;
import lombok.Getter;

@Getter
public enum VipPriceEnum {
    //'Month, quarter, half a year, year, forever
    MONTH(1, "月",2.99,1.0),
    QUARTER(2, "季",5.9,1.0),
    HALF_YEAR(3, "半年",14.9,0.98),
    YEAR(4, "1年",28.9,0.95),
    FOREVER(5, "永久",49.9,0.9);

    private final Integer code;
    private final String name;
    private final Double price;
    private final Double discount;


    VipPriceEnum(Integer code, String name,Double price,Double discount) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.discount = discount;

    }

    public static String getNameByCode(Integer code) {
        for (VipPriceEnum enable : values()) {
            if (enable.getCode() == code) {
                return enable.getName();
            }
        }
        return "";
    }
    public static Double getPriceByCode(Integer code) {
        for (VipPriceEnum enable : values()) {
            if (enable.getCode() == code) {
                return enable.getPrice();
            }
        }
        return 0.0;
    }

    public static Double getDiscountByCode(Integer code) {
        for (VipPriceEnum enable : values()) {
            if (enable.getCode() == code) {
                return enable.getDiscount();
            }
        }
        return 1.0;
    }
}
