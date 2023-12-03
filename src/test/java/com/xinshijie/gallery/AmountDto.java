package com.xinshijie.gallery;

import lombok.Data;

@Data
public class AmountDto {
    //货币类型USD美元 CNY 人民币
    private String   currency_code="USD";
    private String   value;
}
