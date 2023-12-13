package com.xinshijie.gallery.vo;

import lombok.Data;

import java.util.List;

@Data
public class PaymentsVo {
    private List<CaptureVo> captures;
}
