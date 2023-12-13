package com.xinshijie.gallery.vo;

import lombok.Data;

import java.util.List;

@Data
public class PayPalTransactionVo {
    private String id;
    /**
     * COMPLETED	The payment was authorized or the authorized payment was captured for the order.
     * APPROVED	The customer approved the payment through the PayPal wallet or another form of guest or unbranded payment. For example, a card, bank account, or so on.
     */
    private String status;
    private List<PurchaseUnitVo> purchase_units;
    private List<LinkVo> links;


}
