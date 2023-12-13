package com.xinshijie.gallery.dto;

import lombok.Data;

import java.util.List;

@Data
public class PayoutEventDto {
    private String id;
    private String event_version;
    private String create_time;
    private String resource_type;
    private String event_type;
    private String summary;
    private PayoutResourceDto resource;
    private List<PayoutLinkDto> links;

}
