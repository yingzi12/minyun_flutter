package com.xinshijie.gallery.vo;

import lombok.Data;

import java.util.List;

@Data
public class CaptureVo {
    private String id;
    private String status;
    private AmountVo amount;
    private boolean final_capture;
    private String disbursement_mode;
    private String create_time;
    private String update_time;
    private List<LinkVo> links;
}
