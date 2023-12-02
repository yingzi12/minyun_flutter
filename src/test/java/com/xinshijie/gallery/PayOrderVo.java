package com.xinshijie.gallery;

import lombok.Data;

import java.util.List;

@Data
public class PayOrderVo {
    private String id;
    private String status;
    private List<LinkVo> links;

}
