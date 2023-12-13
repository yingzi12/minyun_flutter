package com.xinshijie.gallery.dto;

import lombok.Data;

@Data
public class PurchaseUnitDto {
    /**
     * 购买单元的外部参考ID，用于多个购买单元时通过PATCH更新订单。
     */
    private String reference_id;

    /**
     * 交易或者订单的描述
     * 购买描述。
     */
    private String description;

    /**
     * 用户自定义id
     * API调用者提供的外部ID，用于与PayPal交易对账。
     */
    private String custom_id;

    /**
     * 总订单金额及其细分。
     */
    private AmountDto amount;
    /**
     * 信用卡账单上显示的商户描述。
     */
    private String soft_descriptor;

}
