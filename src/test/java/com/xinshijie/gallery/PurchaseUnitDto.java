package com.xinshijie.gallery;

import lombok.Data;

@Data
public class PurchaseUnitDto {
    /**
     * 购买单元的外部参考ID，用于多个购买单元时通过PATCH更新订单。
     */
    private String referenceId;

    /**
     * 购买描述。
     */
    private String description;

    /**
     * API调用者提供的外部ID，用于与PayPal交易对账。
     */
    private String customId;

    /**
     * 总订单金额及其细分。
     */
    private AmountDto amount;
}
