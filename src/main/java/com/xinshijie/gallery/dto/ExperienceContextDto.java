package com.xinshijie.gallery.dto;

import lombok.Data;

@Data
public class ExperienceContextDto {
    private   String    locale="en-US";
    private   String     return_url = "https://example.com/returnUrl";
    private   String   cancel_url = "https://example.com/cancelUrl";
}
