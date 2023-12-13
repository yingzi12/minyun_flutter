package com.xinshijie.gallery.dto;

import lombok.Data;

@Data
public class PayoutResourceDto {
    private String id;
    private String merchant_id;
    private Destination destination;
    private Amount amount;
    private PayoutStatus payout_status;
    private String create_time;

    public static class Destination {
        private String id;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Amount {
        private String currency_code;
        private String value;

        public String getCurrency_code() {
            return currency_code;
        }

        public void setCurrency_code(String currency_code) {
            this.currency_code = currency_code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class PayoutStatus {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
