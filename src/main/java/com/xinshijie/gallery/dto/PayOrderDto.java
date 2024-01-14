package com.xinshijie.gallery.dto;

import lombok.Data;

import java.util.List;

@Data
public class PayOrderDto {

    /**
     * 订单的购买单元列表，每个单元代表支付者和收款方之间的合同。
     */
    private List<PurchaseUnitDto> purchase_units;

    /**
     * 支付意图，例如："CAPTURE"（立即捕获支付）或 "AUTHORIZE"（授权后支付）。
     */
    private String intent = "CAPTURE";

    private Integer aid;
    private PaymentSourceDto payment_source;


}
